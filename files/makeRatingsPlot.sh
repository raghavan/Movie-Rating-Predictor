cat trainingmodel.csv  | rev | cut -d";" -f1 | rev | sort -n | uniq -c  | sort -n > ratings_count.txt

#use gnuplot ;  plot "ratings_count.txt" using 2:1
