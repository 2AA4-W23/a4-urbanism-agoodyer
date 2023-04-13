#!/bin/bash

# java -jar island/island.jar -lakes 8 -rivers 13 -i img/irregular2.mesh -o img/irregular.mesh -h 1080 -w 1920 -seed MOSSERGIVEUSA90!wwtwggggget!
# java -jar visualizer/visualizer.jar -i img/irregular2.mesh -o img/irregular.svg 



java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 2000 -s 20  -o img/irregular2.mesh
java -jar island/island.jar -lakes 8 -rivers 14 -i img/irregular2.mesh -p Volcano  -cities 20 -o img/irregular.mesh -h 1080 -w 1920 

java -jar visualizer/visualizer.jar -i img/irregular.mesh -o img/irregular.svg 