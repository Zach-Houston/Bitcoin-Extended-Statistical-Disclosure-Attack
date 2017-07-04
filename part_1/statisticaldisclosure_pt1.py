import sys
import operator
from string import ascii_lowercase
class Sender:
	def __init__(self, name):
		self.name = name
		self.rounds = 0
		self.inround = False
		self.u = {}
		self.o = {}
		self.t = 0
		self.tprime = 0
		self.v = {}
		for c in ascii_lowercase:
			for i in range(0,10):
				self.u[c+str(i)] = 0
				self.o[c+str(i)] = 0
				self.v[c+str(i)] = 0
	def newRound(self, words, b):
		if(self.inround == False):
			for word in words:
				self.u[word] += float(1.0/b)
			self.tprime +=1.0
		elif(self.inround == True):
			for word in words:
				self.o[word] += float(1.0/b)
			self.t +=1.0
if len(sys.argv) < 2:
	print "enter the file"
	exit()
file = open(sys.argv[1])
totalrounds = 0
bMsgPerBatch = 32
listoftargets = {}

for c in ascii_lowercase:	# Create a0-z0 list of targets
		listoftargets[c+str(0)] = Sender(c+str(0))

for line in file: # Line Processing Loop
	words = line.translate(None, "',:SR[]").strip().split()	
	if( line[0] == "S" ): # If line is "S" set all a0-z0 in the line inround to True
		for target in listoftargets:
			if target in words:
				listoftargets[target].inround = True
	elif( line[0] == "R" ): # If line is "R" adjust all targets according to whether inround or not
		for target in listoftargets:
			listoftargets[target].newRound(words,bMsgPerBatch)
			listoftargets[target].inround = False
	else:	# Check for end of file
		break;
	totalrounds+=1
if len(sys.argv) > 2: # Option for file output
	newfile= open(sys.argv[2], 'w')
	newfile.truncate()
for target in listoftargets:	# Update/Print/Write Loop
	if(listoftargets[target].t == 0): # Check if t or tprime is zero to avoid division/zero
		continue
	if listoftargets[target].tprime != 0: # Update u and o vectors to divide all elements by t and tprime
		listoftargets[target].u.update((x, y/listoftargets[target].tprime) for x, y in listoftargets[target].u.items())
	listoftargets[target].o.update((x, y/listoftargets[target].t) for x, y in listoftargets[target].o.items())
	for c in ascii_lowercase:
		for i in range(0,10):	#Calculate v
			listoftargets[target].v[c+str(i)] = bMsgPerBatch * listoftargets[target].o[c+str(i)] - (bMsgPerBatch - 1) * listoftargets[target].u[c+str(i)]
	newlist = sorted(listoftargets[target].v.iteritems(), key=operator.itemgetter(1), reverse=True)[:2]
	print "\n" + listoftargets[target].name + ":"	#Print and Write
	print newlist
	print listoftargets[target].name
	print listoftargets[target].v
	items = map(operator.itemgetter(0), newlist)
	if len(sys.argv) > 2:
		newfile.write(listoftargets[target].name + ", " + items[0] + ", " + items[1] + "\n")
