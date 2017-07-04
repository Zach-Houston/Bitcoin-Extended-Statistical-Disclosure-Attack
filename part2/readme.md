
# Part 2

Part 2 has two parts.  First, we had to find the ports that all of the different clients were running on.  

Then, we use a listener to record which clients SEND messages (this is accomplished by listening to all nodes in the network and seeing which node is propogating a new message first).
We also need to record which clients RECEIVE messages (done in a similar way to SENT messages, just see which node propogates an ACK first).


Finally, we use the statistical disclosure attack on our recorded data to get an answer.
NOTE: This answer won't be 100% correct, it will be based on probabilities.  The longer this program records, the greater the accuracy.  The program was ran 100 rounds for the project's results.
