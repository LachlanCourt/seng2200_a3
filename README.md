# SENG2200 - Programming Languages and Paradigms
## Assignment 3
### Task
Create a discrete event simulator to simulate items moving through a production line. A calculation was given to indicate the amount of time an item would spend at each production stage, and a layout of the production line was also given. The program had to demonstrate an understanding of Polymorphism and Generics, and the production line should be easily expandable and adjustable. Production stages should implement blocking and starving depending on the queues that the feed from and output to, and a report should be produced at the end of the simulation to indicate a number of important statistics about the simulation. Bonus points were awarded for the implementation of a Singleton class to assign unique ID's to items moving through the production line.

This particular simulator includes capability to read the production line from a text file and as such the generation of the report had to include dynamic assessment of the production line in order to effectively output the path that items took through the line.
### Compile
`javac PA3.java`
### Run
The program has the capability to read the production line from a text file. Check out `production.txt` to determine the formatting requirements. This is an optional parameter, if not given the program will load a hardcoded default line which matches `production.txt` as per the specification.
- M: Average processing time of a given item in a stage, eg. `1000`
- N: Range of processing time of a given item in a stage, eg. `1000`
- Qmax: Maximum number of items in a queue at any given time, eg `7`

`java PA3 <M> <N> <Qmax> [filename]`
