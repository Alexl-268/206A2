addStats() {                    #this function renws the stats in .stats.txt file according to input
    sed -i '/^$/d' src/stats/.failed.txt                      #delete the word from the following files and add it in later
    sed -i "/^$1/d" src/stats/.failed.txt
    sed -i '/^$/d' src/stats/.faulted.txt
    sed -i "/^$1/d" src/stats/.faulted.txt

    grep -wn "$1" src/stats/.stats.txt > /dev/null      #adding the word to stats if it does not exist before
    if [[ $? = 1 ]] ; then
        printf "%-10s|%+20s\n"  "0 0 0" "$1">> src/stats/.stats.txt
    fi

    if [ $2 -eq 2 ] ; then                            #if the second input is 2 then add to failed word otherwise add to faulted words
        echo "$1" >> src/stats/.failed.txt
    elif [ $2 -eq 1 ]; then
        echo "$1" >> src/stats/.faulted.txt
    fi

    lineFound="$(grep -wn "$1" src/stats/.stats.txt | cut -f1 -d":")"                     #finding the line of the word
    actualStat="$(head -$lineFound src/stats/.stats.txt | tail -1 | cut -f1 -d"|")"       #getting the first 3 values in the line
    stats=( $actualStat )                                                       #turning it into an array

    incrementVal=${stats[$2]}                                                   #increment the desired stat
    incrementVal=$((1+incrementVal))

    stats[$2]=$incrementVal
    sed -i ""$lineFound"d" src/stats/.stats.txt
    sed -i '/^$/d' src/stats/.stats.txt                                                   #delete the line and replace with the new stat below
    printf "%-10s|%+30s\n"  "${stats[0]} ${stats[1]} ${stats[2]}" "$1">> src/stats/.stats.txt
}
addStats $1 $2
