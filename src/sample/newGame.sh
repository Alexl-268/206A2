speak() {                       #speaking function
    echo $1 | festival --tts
}

addStats() {                    #this function renws the stats in .stats.txt file according to input
    lineFound="$(grep -wn "$1" src/stats/.stats.txt | cut -f1 -d":")"                     #finding the line of the word
    actualStat="$(head -$lineFound src/stats/.stats.txt | tail -1 | cut -f1 -d"|")"       #getting ht efirst 3 values in the line
    stats=( $actualStat )                                                       #turning it into an array

    incrementVal=${stats[$2]}                                                   #increment the desited stat
    incrementVal=$((1+incrementVal))

    stats[$2]=$incrementVal
    sed -i ""$lineFound"d" src/stats/.stats.txt
    sed -i '/^$/d' src/stats/.stats.txt                                                   #delete the line and replace with the new stat below
    printf "%-10s|%+20s\n"  "${stats[0]} ${stats[1]} ${stats[2]}" "$1">> src/stats/.stats.txt
}

spellingGame() {                            #reusable new game function
    grep -wn $1 src/stats/.stats.txt > /dev/null      #adding the word to stats if it does not exist before
    if [[ $? = 1 ]] ; then
        printf "%-10s|%+20s\n"  "0 0 0" "$1">> src/stats/.stats.txt
    fi
#
    speak "spell"                                   #first attempt
    speak "$1"
    read -p "Spell" spelling
    sed -i '/^$/d' src/stats/.failed.txt                      #delete the word from the following files and add it in later
    sed -i '/^$/d' src/stats/.faulted.txt
    sed -i "/^$1/d" src/stats/.failed.txt
    sed -i "/^$1/d" src/stats/.faulted.txt

    if [ "${spelling^^}" != "${1^^}" ] ; then       #first incorrect attempt
        speak "Incorrect, Try once more"
        speak "$1"
        speak "$1"
        read -p "Incorrect" spelling;
        if [ "${spelling^^}" != "${1^^}" ] ; then   #second incorrect attempt, add it to .failed.txt, increment failed stat
            speak "Incorrect"
            echo "$1" >> src/stats/.failed.txt
            addStats "$1" 2
        else                                        #second attempt sucessful, add it to .faulted.txt, increment faulted stat
            speak "correct"
            echo $1 >> src/stats/.faulted.txt
            addStats "$1" 1
        fi
    else                                            #mastered attempt, increment master stat
        speak "correct, well done"
        addStats "$1" 0
    fi

    echo finished
}
touch src/stats/.stats.txt src/stats/.failed.txt src/stats/.faulted.txt

