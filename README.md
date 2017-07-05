# Bitcoin-Statistical-Disclosure-Attack
This project was designed to be used on a network similar to Bitcoin.  It was a project for my Network Security class.

https://books.google.com/books?id=Sd3zBwAAQBAJ&pg=PA17&lpg=PA17&dq=practical+traffic+analysis:+extending+and+resisting+statistical+disclosure&source=bl&ots=CTV1hNzCdY&sig=pwbckkDxKFVm1ob6lShUg6bK-a8&hl=en&sa=X&ved=0ahUKEwja_oDKjPPUAhXKZiYKHQOjBSYQ6AEISjAG#v=onepage&q=practical%20traffic%20analysis%3A%20extending%20and%20resisting%20statistical%20disclosure&f=false

## Part 1
Part 1 simply involved performing a statistical disclosure attack on a given set of rounds data.

## Part 2
Our goal is to perform a statistical disclosure attack on all the public nodes in the network.  To start, we are given one node to connect to.  From there, we can use the PEERS command to find all of the other nodes in the network.
  After that, we simply have to connect to the nodes and listen in to get our rounds data.
  
## Part 3
Our goal is to perform a statistical disclosure attack on all the private nodes in the network.  These nodes aren't able to be directly connected to, so we can't simply listen to them to determine who is talking.  Instead, we first create a list of private nodes and their corresponding public nodes.  This allows us to listen to them by observing what combination of public nodes is first propogating a message by comparing the speaking nodes to the ones in the list of private-to-public nodes.  This gives us our rounds data.

# Network Specifications
___Basics___

• All Strings are transmitted using UTF-8. 

• All IDs (node and message) are transmitted as 64 character long strings representing hex strings. 

• All node IDs start with “8” • All messages start with either “F” or “0” depending on the reachability of the node originating the message 

• All messages are 1024 characters long representing hex strings. 

• All IP addresses are assumed to be IPv4. 

• All commands will start with a command string followed by the newline character, followed by the command appropriate data.


___Command Messages___

__PEERS__

• Nodes will respond by returning a random list of 20 peers that this node is currently connected to, either via incoming or outgoing 
connections. 

• Peers are returned, each separated by the newline character. 

• Peers are returned in the form <Peer ID Value>@<IP Address>:<TCP Port>, where all information is the string representation of the appropriate data.

__OFFER__

• Nodes will, upon cryptographic veriﬁcation of a message they are originating or received via PULL, send this message to all connected peers informing them that they have the ability to transmit the message. 

• The peer will follow the command word with the message’s ID, followed by the newline character.

__PULL__

• This command allows a node to request message data matching a particular ID in response to an OFFER message. 

• Nodes will follow this command with the message ID, followed by a newline. 

• A DATA command should follow a successfully completed PULL command.

__DATA__

• This command proceed the transmission of data in response to a pull request. 

• Nodes will follow this command with the message ID, followed by a newline, followed by the message itself, followed by a newline.

__ACK__

• Nodes will, upon cryptographic ﬁrst veriﬁcation of an acknowledgment for a particular message, send this message to all peers. 

• Acknowledgments contain a zero knowledge proof that the node originating the message was the recipient of one message for the prior round, but does not reveal which message to any peer other than the message originator. 

• The peer will follow the command word with the compact proof, in the form of 128 characters in a hex encoding, followed by the newline.
