# Bitcoin-Statistical-Disclosure-Attack
This project was designed to be used on a network similar to Bitcoin.  It was a project for my Network Security class.

## Part 1
Part 1 simply involved performing a statistical disclosure attack on a given set of rounds data.

## Part 2
Our goal is to perform a statistical disclosure attack on all the public nodes in the network.  To start, we are given one node to connect to.  From there, we can use the PEERS command to find all of the other nodes in the network.
  After that, we simply have to connect to the nodes and listen in to get our rounds data.
  
## Part 3
Our goal is to perform a statistical disclosure attack on all the private nodes in the network.  These nodes aren't able to be directly connected to, so we can't simply listen to them to determine who is talking.  Instead, we first create a list of private nodes and their corresponding public nodes.  This allows us to listen to them by observing what combination of public nodes is first propogating a message by comparing the speaking nodes to the ones in the list of private-to-public nodes.  This gives us our rounds data.
