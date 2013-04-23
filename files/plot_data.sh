#!/bin/bash

usage="Usage: plot_data.sh <goal_file> <trajectory_file> <alpha_beta_prob_file> <output_file> "

if [ $# -lt 3 ]; then
	echo $usage
	exit 1
fi

rm goal_plot.txt traj_plot.txt

gawk 'ARGIND==1{ if(NR>1) {prob[FNR]=$3; if (min > int($3)) min = int($3) - 1}} \
ARGIND==2{ gy[$1]=$2 } \
ARGIND==3{ \
if(gy[$1]==$2){ goal_prob[$1","$2]=prob[FNR+1]} \
else {traj_prob[$1","$2]=prob[FNR+1]}\
}\
END{for (point in goal_prob) { cnt=split(point,coord,","); \
if(cnt == 2) {print coord[1]" "coord[2]" "goal_prob[coord[1]","coord[2]]" "int(goal_prob[coord[1]","coord[2]]-min)+1 > "goal_plot.txt"}\
 }\
 for (point in traj_prob) { cnt=split(point,coord,","); \
if(cnt == 2) {print coord[1]" "coord[2]" "traj_prob[coord[1]","coord[2]]" "int(traj_prob[coord[1]","coord[2]]-min)+1 > "traj_plot.txt"}\
 }\
} \
' $3 $1 $2

gnuplot << EOF
set terminal pdf enhanced color fsize 11 lw 1.5 size 8in, 10in
set output "$4"
#set key bottom
reset
unset border
unset xtics
unset ytics
set lmargin screen .1
set rmargin screen .9
set tmargin screen .9
set bmargin screen .1

px=NaN
py=NaN
dx(x) = (xd = x-px, px = x, xd) 
dy(y) = (yd = y-py, py = y, yd)  
pointsize(z) = (z+log(z))
plot "$2" using (px):(py):(dx(\$1)):(dy(\$2)) with vectors lt rgb "#555555" title "", \
	"traj_plot.txt" using 1:2:4 with points lt 3 pt 6 ps var title "",\
	"goal_plot.txt" using 1:2:4 with points lt 1 pt 6 ps var title ""
#EOF # not taking it... why???

#open goal_prob_plot.pdf