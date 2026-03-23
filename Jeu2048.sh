#!/bin/bash
xdotool mousemove 1280 1024
cd projet/2048
touch highscore
java -cp .:../..:/home/pi/git/MG2D Main
