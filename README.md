# CSX42: Assignment 3
**Name:** Rashmi A.Badadale

-----------------------------------------------------------------------

Following are the commands and the instructions to run ANT on your project.


Note: build.xml is present in csx42-summer-2020-assign3-rashmi1112\studentskills\src  folder.

## Instruction to clean:

```commandline
ant -buildfile studentskills/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

## Instructions to compile:

```commandline
ant -buildfile studentskills/src/build.xml all
```
The above command compiles your code and generates .class files inside the BUILD folder.

## Instructions to run:

```commandline
ant -buildfile studentskills/src/build.xml run -Dinput="input.txt" -Dmodify="modify.txt" -Dout1="output1.txt" -Dout2="output2.txt" -Dout3="output3.txt" -Derror="error.txt" -Ddebug="(debug_value)"
```
Note: Arguments accept the absolute path of the files.

## Description:

The program takes 7 arguments, input file, modify file, 3 output files,error file and debug value. The program parses the input for required strings and perform respective operations based on the 
parsed input. The goal of the assignment is to implement a replica system using observer pattern. 

I have used AVL trees for creating and storing the student records and then printing the required fields(BNumber and Skills) from the records. 
The reason I selected AVL trees is because it has faster access and insertion time than usual binary search trees. AVL trees have O(logN) as worst case complexity for both searching and insertion.
Though Red-Black trees are preferred for database purpose, I considered AVL trees as they are easier to implement (than Red-Black trees). 

I have used the following reference for creating,inserting and balancing of the nodes into the AVL tree. 

https://codewithmosh.com/courses

Implementation of Observer Patter: 

In this program, 2 replicas of each student record node are created. Initially, when one node is created from the input file, it is implemented as a subject and 2 clones of the node are created. 
These 2 clones are then implemented as listeners of the subject node. Any changes made to the subject node will be notified to the listener nodes. Since, this is a replica system, the other nodes are 
also implemented as subjects and the remaining 2 nodes are implemented as listeners of the subject node. 

In this way, each node is a subject and each node is a listener for other 2 nodes as well. In my program, the function cloneNodes() actually implements a loop where each node is implemented as subject and 
other 2 nodes are subscribed to it. Then we move to the next node and do the same with the remaining nodes. In this way, each node is registered to each other and hence, any changes 
made to any of the nodes will be notified to its subscribers, henceforth the other 2 nodes.

A String  builder is used to store the output result and then passed on to an output file through a buffered writer and also to the standard output console. 
Also, another stringBuilder is used to store the error messages and print them into the error file. 


## Academic Honesty statement:

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: [06/24/2020]


