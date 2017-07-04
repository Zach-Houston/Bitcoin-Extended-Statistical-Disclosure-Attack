import socket;
import sys


class BreakIt(Exception): pass


class nodes:
    def __init__(self, port, msg):
        self.PORT = port
        self.MSG = msg


class pnodes:
    def __init__(self, port, msg):
        self.PORT = port
        self.MSG = msg
        self.dict = {}


def worker(hostIP, port):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((hostIP, port))
    peers = "PEERS\n"
    s.send(peers.encode('UTF8'))
    for i in range(0,2):
        output = s.recv(1024)
        formatstring = output.replace("@", " ")
        formatstring = formatstring.replace(":", " ")
        line = formatstring.split()
        if i == 0:
            try:
                b = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                b.connect((hostIP, int(line[2])))
                if line[2] not in dict:
                    dict[int(line[2])] = nodes(int(line[2]), line[0])
            except Exception as e:
                if line[2] not in pdict:
                    pdict[int(line[2])] = pnodes(int(line[2]), line[0])
                    pdict[int(line[2])].dict[port] = port
                else:
                    pdict[int(line[2])].dict[port] = port
                b.close()
            finally:
                b.close()
        if i == 1:##7
            try:
                b = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                b.connect((hostIP, int(line[2])))
                if line[2] not in dict:
                    dict[int(line[2])] = nodes(int(line[2]), line[0])
            except Exception as e:
                if line[2] not in pdict:
                    pdict[int(line[2])] = pnodes(int(line[2]), line[0])
                    pdict[int(line[2])].dict[port] = port
                else:
                    pdict[int(line[2])].dict[port] = port
                b.close()
            finally:
                b.close()

            try:
                b = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                b.connect((hostIP, int(line[5])))
                if line[5] not in dict:
                    dict[int(line[5])] = nodes(int(line[5]), line[3])
            except Exception as e:
                if line[5] not in pdict:
                    pdict[int(line[5])] = pnodes(int(line[5]), line[3])
                    pdict[int(line[5])].dict[port] = port
                else:
                    pdict[int(line[5])].dict[port] = port
                b.close()
            finally:
                b.close()

            try:
                b = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                b.connect((hostIP, int(line[8])))
                if line[8] not in dict:
                    dict[int(line[8])] = nodes(int(line[8]), line[6])
            except Exception as e:
                if line[8] not in pdict:
                    pdict[int(line[8])] = pnodes(int(line[8]), line[6])
                    pdict[int(line[8])].dict[port] = port
                else:
                    pdict[int(line[8])].dict[port] = port
                b.close()
            finally:
                b.close()

            try:
                b = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                b.connect((hostIP, int(line[11])))
                if line[11] not in dict:
                    dict[int(line[11])] = nodes(int(line[11]), line[9])
            except Exception as e:
                if line[11] not in pdict:
                    pdict[int(line[11])] = pnodes(int(line[11]), line[9])
                    pdict[int(line[11])].dict[port] = port
                else:
                    pdict[int(line[11])].dict[port] = port
                b.close()
            finally:
                b.close()

            try:
                b = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                b.connect((hostIP, int(line[14])))
                if line[14] not in dict:
                    dict[int(line[14])] = nodes(int(line[14]), line[12])
            except Exception as e:
                if line[14] not in pdict:
                    pdict[int(line[14])] = pnodes(int(line[14]), line[12])
                    pdict[int(line[14])].dict[port] = port
                else:
                    pdict[int(line[14])].dict[port] = port
                b.close()
            finally:
                b.close()

            try:
                b = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                b.connect((hostIP, int(line[17])))
                if line[17] not in dict:
                    dict[int(line[17])] = nodes(int(line[17]), line[15])
            except Exception as e:
                if line[17] not in pdict:
                    pdict[int(line[17])] = pnodes(int(line[17]), line[15])
                    pdict[int(line[17])].dict[port] = port
                else:
                    pdict[int(line[17])].dict[port] = port
                b.close()
            finally:
                b.close()

            try:
                b = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                b.connect((hostIP, int(line[20])))
                if line[20] not in dict:
                    dict[int(line[20])] = nodes(int(line[20]), line[18])
            except Exception as e:
                if line[20] not in pdict:
                    pdict[int(line[20])] = pnodes(int(line[20]), line[18])
                    pdict[int(line[20])].dict[port] = port
                else:
                    pdict[int(line[20])].dict[port] = port
                b.close()
            finally:
                b.close()
    s.close()


def arewedone():
    for privatenode in pdict.values():
        if privatenode.dict.__len__() < 8:
            return False
    return True


def printnodes():
    for privatenode in pdict.values():
        sys.stdout.write("Private Node: " + str(privatenode.PORT) + " Public Nodes: ")
        for snode in privatenode.dict:
            sys.stdout.write(" " + str(snode))
        print
    print "DONE--------------------------------------------------------------------"

def recursive_node_find():
    hostIP = "REMOVED FOR PRIVACY"
    initPort = 15066
    worker(hostIP, initPort)
    try:
        while(1):
            for port in dict.keys():
                worker(hostIP, port)
                printnodes()
                if arewedone():
                    raise BreakIt
    except BreakIt:
        pass


dict = {} ##Key = Port ------------- Value == nodes class
pdict = {} ##Key = Port ------------- Value == pnodes class
recursive_node_find()

f = open("Nodes_data", "w")
for node in dict.values():
    f.write('%s %d\n' % (node.MSG, node.PORT))

f2 = open("pnodes_data", "w")
for pnode in pdict.values():
    f2.write('%s %d:' % (pnode.MSG, pnode.PORT))
    for privatedict in pnode.dict:
        f2.write(' %d' % (privatedict))
    f2.write('\n')





