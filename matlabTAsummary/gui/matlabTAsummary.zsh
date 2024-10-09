#!/usr/bin/env zsh		# should be generic
#!/bin/zsh			# for Mac
#
# we can select sh or ksh emulation with
#emulate sh
# or
#if [ -n "$ZSH_VERSION" ]; then emulate -L ksh; fi
#
# matlabTAsummary.zsh
#
# find all log_*.txt files and process them to remove spots with a
# diffudion below the threshold and convert them to tab-separated
#
# XXX JR XXX
#	NOTE check that there are no empty fields
#
# examples:
#	zsh ~/bin/matlabTAsummary.zsh -f 'data' -d '0.01' -i '100'


MYNAME="$0"
opts="hd:i:f:;help,diffusion:,intensity:,folder:"	# option string (GNU getopt)
optss="hd:i:f:"		# option string (bash getopts)
diffusion=0.0			# default minimum values
intensity=0
folder='.'
function usage() {
    local opts=$1
    cat <<END
$MYNAME [-h] [--help] [-d #] [--diffusion #] [-i #] [--intensity #] [-f folder] [--folder folder]
                -h 	print this help
		-d #	specify a diffusion threshold 
			for filtering spots: only show spots with 
			diffusion above (not equal) than the threshold.
			If none is specified, 0 will be used as default.
		-i #	specify an intensity threshold
			for filtering spots: only show spots with 
			intensity above (not equal) than the threshold.
			If none is specified, 0 will be used as default.
		-f folder	the folder containing 
			the experiment directories to summarize. If 
			none is specified, then the current folder will
			be used as default.
END
}


## args
#
# Process command line using built-in getopts (which only
# accepts one-dash, one-character options)
function args() {
    local opts="$1"	# get option string
    shift		# and remove it from the command line
    while getopts "$opts" op ; do
        case "${op}" in
	    h) usage 
	       exit 0
	       ;;
	    d) diffusion=${OPTARG}
	       ;;
	    i) intensity=${OPTARG} 
	       ;;
	    f) folder="${OPTARG}"
	       ;;
	    :)		# If expected argument omitted:
               echo "Error: -${OPTARG} requires an argument."
               usage
	       exit 1                       # Exit abnormally.
               ;;
            *)                              # If unknown (any other) option:
               usage
	       exit 1                       # Exit abnormally.
               ;;
	    ?) 
               usage
	       exit 1                       # Exit abnormally.
               ;;
	esac  
    done
}



##
##	Problem-specific code starts here
##


function clean_spaces() {
    local file="$1"
    if [ ! -s "$file" ] ; then
        echo "ERROR: $file does not exist or is empty" 1>&2
	return;
    ###else echo "Processing $file" 1>&2
    fi
    # generate header line
    # remove ' ' after '=', then change '='+not-spaces by '\t' 
    # and finally any remaining spaces
    head -1 "$file" \
    | sed -e 's/= /=/g' \
        -e 's/=[^ ]\+/\t/g' \
	-e 's/ //g' \
	-e 's/diffusionCoefficient/diffusion/g'
    # change non-'='s+'=' by '\t' and then remove leading '\t' 
    cat "$file" | sed -e 's/[^= ]\+=/\t/g' -e 's/^\t//g'
}



# call with get_index value "$arr[@]"
function get_index() {
    # $1 is the value to look up
    # $2 is the array content (substrings cannot contain spaces)
    # we will use the name to access the array
    local val=$1 ; shift
    local arr=( "$@" )		# preserve empty elements
    
    ### >&2 echo "$arr[@]"
    for i in {1..$#arr} ; do
	### >&2 echo "Checking element #$i of '${#arr}' in '${arr[@]}' "$'\t'"'${arr[$i]}' against '$val'"
	### >&2 echo "arr_$i = ${arr[$i]}"
	### >&2 echo "v = $val"
	if [[ "${arr[$i]}" == "$val" ]] ; then
	    echo $i
	    return 0	# success
	fi 
    done
    echo -1
    return 1		# failure
}

# this version works with zsh
function filter_diffusion_intensity() {
    local diffusion="$1"
    local intensity="$2"
    ###echo "DIF=$diffusion INT=$intensity"		###
    # ensure they are numeric values (or zero)
    diffusion=$(( diffusion ))
    intensity=$(( intensity ))

    read line		# get header line
    >&2 echo ""						###
    >&2 echo "HEAD: $line"				###
    echo "$line"	# output header
    
    #	we can do it with an associative array, but also with a normal array:
    declare -a H
    local i=1		# in zsh arrays use 1-offset
    while read ln ; do H[$((i++))]="$ln"; done <<< $(echo "$line" | tr '\t' '\n' ) 
    
    #declare -A H
    #i=0
    #while read ln ; do H[$ln]=$((i++)); done <<< $(echo $line | tr ' ' '\n' ) 
    # now H (header) is an associative array that can be used to get indexes
	### >&2 echo $'\n\n'"LINE=$line"$'\n'"HEADER($#H)=${H[@]}"
    
    # we have consumed and produced the header line, let's go for the rest
    # reading each line as an array (equivalent to bash read -a line)
    dif_index=$( get_index 'diffusion' "$H[@]" )
    ### >&2 echo "dif_index=$dif_index"
    ### >&2 echo "LINE='${line}' d='$dif_index'"

    int_index=$( get_index 'spotCorrectedIntensity' "${H[@]}" )
    ### >&2 echo "int_index=$int_index"

    while read -A line ; do
        ### >&2 echo ""						###
        ### >&2 echo "LINE($#line)='${line[@]}' i='$int_index' d=$dif_index"	###
        dif="${line[ $dif_index ]}"
	int="${line[ int_index ]}"
	### >&2 echo -e "DIF=${line[ dif_index ]}\tINT=${line[ int_index ]}"	###
	# ensure they are numbers (or, if not, that they are zero)
	dif=$(( dif ))
	int=$(( int ))
	
	# for bc comparison we need exponential notation to be in caps
	### >&2 echo "TEST: (${dif/e/*10^} > ${diffusion/e/*10^} ) && ( ${int/e/*10^} > ${intensity/e/*10^} )"
	# or use '*10^' instead or 'e'
#	dif=${dif/e/*10^} ; diffusion=${diffusion/e/*10^}
#	int=${int/e/*10^} ; intensity=${intensity/e/*10^}
	# bc must be the last command in the pipeline so we get its result
	### >&2 echo "echo \"( ${dif/e/*10^} > ${diffusion/e/*10^} ) &&\" \
	###      \"( ${int/e/*10^} > ${intensity/e/*10^} )\" \
	### | bc -l"
#	res=$( echo "( ${dif/e/*10^} > ${diffusion/e/*10^} ) && " \
#	            "( ${int/e/*10^} > ${intensity/e/*10^} )" \
#	       | bc -l )
#	### >&2 echo "RES=$res"					###
#	if [ "$res" -eq "1" ] ; then
	if (( $dif > $diffusion && $int > $intensity )) ; then
	    echo "$line"	# output passing line
	>&2 echo "PASS: $line"  			    ###
	else >&2 echo "FAIL: $line"			    ###
	fi
    done    
}


# extract sample name from file name
#	There are zsh-specific tricks that can do the same, but this
#	translates better to bash
function sample_from_filename() {
    local file="$1"
    # WE ASSUME WE HAVE BEEN CALLED WITH AN EXPERIMENT OUTPUT FILE
    # This only works if we are at the experiment level
    #sample=${file#./}
    #sample=${sample%%/*}
    
    local sample="$file"
    ###>&2 echo "FILE IS '$sample'"
    # remove trailing hierarchy: this won't remove anything if
    # this is not an experiment, but as we run 'find' before
    # calling this function, we know we should have got an
    # experiment as argument
    sample="${sample%/results/TrackingPackage/tracks/*}"
    ###>&2 echo "EXP FOLDER IS '$sample'"
    # remove leading hierarchy
    # since we run find for . we know all filenames start with ./
    # we have removed the trailing slash so we can now remove the
    # longest prefix match of */
    sample=${sample##*/}
    ###>&2 echo "EXP IS '$sample'"

    echo "$sample"
}


function filter_log_files() {
    local folder="$1"
    local diffusion="$2"
    local intensity="$3"

    # Create the filtered files
    # 	if there are no log files, then nothing will happen
    local i=0
    #find . -name 'log_diffusionCoefficientsAndIntensitiesShort.txt' -print \
    #find . \( -name 'log_*Short.txt' -o -name 'log_*Long.txt' \) -print \
    find "$folder" \( -name 'log_*Short.txt' -o -name 'log_*Long.txt' \) -print \
    | while read file ; do
        # if $file doesn't exist or is empty
        if [ ! -s "$file" ] ; then continue ; fi
	# ${file%.txt} remove shortest match from tail
	out="${file%.txt}.d=$diffusion.i=$intensity.filtered.csv"
	sample=`sample_from_filename "$file"`
	>&2 echo ""						###
	>&2 echo "SAMPLE NAME IS '$sample'"			###
	>&2 echo "INPUT FILE IS  '$file'"			###
	>&2 echo "FILTERED FILE IS '$out'"			###
	# clean spaces and output the file without spaces
	# filter and output the clean file without non-complying lines
	clean_spaces "$file" \
	| filter_diffusion_intensity "$diffusion" "$intensity" \
	${comment# | tee "${out}"} \
	> "${out}"
	###((i++))						###
	###echo $i						###
	###if [ $i -gt 3 ] ; then break ; fi			###
	###if echo "$file" | grep -q Long ; then break ; fi	###
    done
}



# Make summary of current directory
# 	it is assumed that the current directory contains experiment data
#	somewhere in its lower hierarchy
#	if there are no experiments the summary will be empty (except for
#	the header line)
function make_summary() {
    local folder="$1"
    local diffusion="$2"
    local intensity="$3"
    
    folder=$( realpath "$folder" )
    summ_file="$folder/all_together.d=$diffusion.i=$intensity.csv"
    # output header
    echo "sample	spot	diffusion	spotCorrectedIntensity	type" \
         > $summ_file

    find "$folder" -name "log_*.d=$diffusion.i=$intensity.filtered.csv" \
    | while read file ; do
        sample=`sample_from_filename "$file"`
	len=`wc -l "$file" | cut -d' ' -f1`
        ###>&2 echo ""
        ###>&2 echo "SAMPLE NAME IS '$sample'"
        ###>&2 echo "FILE $file IS $len LINES LONG"
	
	# if it is empty or only contains a header line, ignore it
        if [ $len -le 1 ] ; then continue ; fi
	LONG=1; SHORT=1;
	# use zsh/pcre module to search for kind of report file
	if [[ "$file" =~ "Long.d=" ]] ; then LONG=0 ; fi
	if [[ "$file" =~ "Short.d=" ]] ; then SHORT=0 ; fi
	### >&2 echo "$LONG"
	### >&2 echo "$SHORT"

	# we do not expect these inside experiments, but might happen
	# somewhere else however unlikely
	if [ "$LONG" -eq 0 -a "$SHORT" -eq 0 ] ; then
	    >&2 echo "IGNORING: '$file'"
	    >&2 echo "WARNING: cannot be both Long and Short format"
	    continue
	elif [ "$LONG" -eq 1 -a "$SHORT" -eq 1 ] ; then
	    >&2 echo "IGNORING: '$file'"
	    >&2 echo "WARNING: is neither Long nor Short formatted"
	    continue
	### else >&2 echo "PROCESSING: '$file'"
	fi

        if [ "$LONG" -eq 0 ] ; then
            # spot is column 1
	    # movementType is column 2
	    # MSSFirstMoment is column 3
	    # spotCorrectedIntensity is column 4
	    # diffusion is column 5
	    # maskRegion is column 6
	    #	we can't use cut because we cannot specify output field order
	    tail -n +2 "$file" \
	    | while read spot mov mss int diff mask ; do
		    echo "$sample	$spot	$diff	$int	long"
	    done
        else    
            # spot is column 1
	    # diffusion is column 2
	    # spotCorrected Intensity is column 3
	    # maskRegion is column 4
	    #	we can't use cut because we cannot specify output field order
	    tail -n +2 "$file" \
	    | while read spot diff int mask ; do
		    echo "$sample	$spot	$diff	$int	short"
	    done
        fi
    ###done | tee -a $summ_file
    done >> "$summ_file"
}


# Summarize a hierarchy
#	what we want is to make one table at each level above the
# experiment level until the specified one
#	we know the experiment level because at that level each directory
# corresponds to one experiment. We can tell when a folder belongs to one
# experiment by looking for subfolders
#	$exp_folder/results/TrackingPackage/tracks/
#	$exp_folder/videoseq
# then, on any folder that contains one or more $exp_folders we will do
# a summary
#
#	So, we need to traverse the folder tree recursively, and when we
# find a exp_level folder (one that contains one or more experiments or
# exp_folders) we should
#	make a summary
#	go up levels and make more summaries as well
#	This is likely easier to achieve recursively:
#
#	set "HAS_EXP" to false to start with
#	for each element in our directory
#	    test if it is a directory
#	    if it is
#		test if it is an exp_folder
#		it is then set "CONTAINS EXP"
#		if it is not, then 
#		    call ourselves on it
#		    if it contained an exp_folder
#			set "HAS_EXP"
#	if we or one of our descendant directories contained an experiment
#	HAS_FOLDER will be true
#		make a summary of this directory tree
#	return "HAS_EXP"
#
#	But we could also do it with pushd/popd
#
function summarize_hierarchy() {
    local hierarchy="$1"	# hierarchy to explore
    local diffusion="$2"	
    local intensity="$3"
    
    hierarchy=`realpath "$hierarchy"`
    >&2 echo "ENTERING $hierarchy"
    cd "$hierarchy"
    local HAS_EXP=1		# FALSE
    while read entry ; do
        if [ -d "$entry" ] ; then
	    if [ -d "$entry/results/TrackingPackage/tracks" ] ; then
	        # $entry is an experiment and we are at an exp_level
		###>&2 echo "EXP FOUND: $entry"
		HAS_EXP=0	# TRUE
		
	    else
	        # $entry is a subdirectory but not an experiment, so we
		# need to recurse on it
		if summarize_hierarchy "$entry" "$diffusion" "$intensity" ; then
		    # $entry contains one or more experiments
		    HAS_EXP=0	# TRUE
		fi
            fi
	fi
    done <<< $( ls -1 )
    
    ###>&2 echo "HAS_EXP($HAS_EXP) $hierarchy"
    if [ "$HAS_EXP" -eq 0 ] ; then
        >&2 echo $'\n'"MAKING SUMMARY FOR" `basename $hierarchy`
	make_summary "." "$diffusion" "$intensity"
    fi
    >&2 echo "EXITING $hierarchy"
    cd ..
    return "$HAS_EXP"
}






#
#
#	EXECUTABLE CODE STARTS HERE
#
#

#echo "$#"
#echo "$@"
# process args and set specified values (if any)
args "$opts" "$@"
	
#echo "i=$intensity"
#echo "d=$diffusion"
#echo "f=$folder"



# go to working folder
if [ -d "$folder" ] ; then
    folder=`realpath "$folder"`
    cd "$folder"
else
    >&2 echo "'$folder' is not a directory"
    exit 1
fi
#echo $( pwd )


echo "Filtering log files..."
filter_log_files "$folder" "$diffusion" "$intensity"

# Create diffusion/intensity tables
# we could do it in the loop above while filtering, but that might get
# too messy, specially for all intermediate directories, so we'll do it
# separately here.

echo $'\n'"Summarizing selected folder and subfolders..."
summarize_hierarchy "$folder" "$diffusion" "$intensity"

