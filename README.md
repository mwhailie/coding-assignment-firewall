# coding-assignment-firewall

This is a git repo for Illumio take‐home coding assignment. The subjective of this project is to implement a firewall that takes a set of predetermined security rule and determine whether a traffic should be allowed or blocked.

## Time with space tradeoff

The key requirment of time and space is that 

*"There may be a massive number of rules (use 500K entries as a baseline), and real world firewalls must be able to store this in a reasonably compact form while introducing only negligible latency to incoming and outgoing network traffic. "*

My solution is to use a map to store the rule, with each existing port number as a key and its corresponding IP ranges as a set of [Interval](https://github.com/mwhailie/coding-assignment-firewall/blob/master/src/com/illumio/Interval.java)as its value. Using such map trades space for time. It takes contants time when querying a port and O(N) time when querying an ip, when N is the number of ip ranges.

## Test

I created a series of units test with both happy path and invalid input in [TestDiver](https://github.com/mwhailie/coding-assignment-firewall/blob/master/src/com/illumio/TestDriver.java).

I also used a dummy .csv file to test performance of the program and it works fines under a massive number of rules(500K entries). 

## Optimization
First, this problem can be solved as a 2D geometrics problem with Segment tree, if we see ip range as X-axis and port range as Y-axis. But I don't have enough time to implement that. If I had more time, I would implement a segment tree datastructure. This would optimize the solution to O(log(N * N)) time.

Another idea is to merge ip ranges and port ranges while adding rules to the firewall. For example,if we have 2 rules
```
inbound,tcp,10000,192.168.10.11
inbound,tcp,10000-20000,192.168.10.11
```
the first rule can be merged into the second rule so that it doesn't need to be added into the firewall. It would save memory when dealing with a large number of rules.

## Team Preference

1. Platform team
2. Policy team
3. Data team
