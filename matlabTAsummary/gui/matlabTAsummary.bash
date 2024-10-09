#
#
# find all log_*.txt files and process them to remove spots with a
# diffudion below the threshold and convert them to tab-separated
#
# XXX JR XXX
#	NOTE check that there are no empty fields
#
# example:
#	bash ~/bin/matlabTAsummary.bash --folder='data' --diffusion='0.01' --intensity='100'


MYNAME="$0"
opts="hd:i:f:;help,diffusion:,intensity:,folder:"	# option string (GNU getopt)
optss="hd:i:f:"		# option string (bash getopts)
diffusion=0.0			# default minimum values
intensity=0
folder='.'

# for use with long and short options
function usage() {
    local opts=$1
    cat <<END
$MYNAME [-h] [--help] [-d #] [--diffusion #] [-i #] [--intensity #] [-f folder] [--folder folder]
                -h | --help	print this help
		-d # | --diffusion #	specify a diffusion threshold 
			for filtering spots: only show spots with 
			diffusion above (not equal) than the threshold.
			If none is specified, 0 will be used as default.
		-i # | --intensity #	specify an intensity threshold
			for filtering spots: only show spots with 
			intensity above (not equal) than the threshold.
			If none is specified, 0 will be used as default.
		-f folder --folder folder	the folder containing 
			the experiment directories to summarize. If 
			none is specified, then the current folder will
			be used as default.
END
}

# override for use with short options only
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


# only works on bash >= v4 (uses name reference)
function get_index() {
    # $1 is the name of the array (not the array content)
    # we will use the name to access the array
    #local -n arr=$1	# use -n to make a reference to the variable *name*
  		   	# from here on we can use arr as the array called as $1
    local arr=( $1 )
    local val=$2

    ###>&2 echo "arr = ${arr[@]}"
    ###>&2 echo "IDX = ${!arr[@]}"

    # loop over the indexes to avoid dereferencing a gap
    for i in ${!arr[@]} ; do
        ###>&2 echo "Checking $i in '${arr[@]}' from '${!arr[@]}' against '$val'"
	if [[ ${arr[i]} = "$val" ]]; then
	    echo $i
	    return 0	# success
	fi
    done
    echo -1
    return 1		# failure
}


function dump_array() {
    # works for linear and for associative arrays
    local -n ary=$1
    for i in "${!ary[@]}"; do
        printf "%s\t%s\n" "$i" "${ary[$i]}"
    done
}


## args
#
# Process command line using built-in bash getopts (which only
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
	esac  
    done
}


## arg
#
# Process command line using external GNU getopt utility
#	NOTE that MacOSX and FreeBSD come with a non-GNU
#	trivial getopt that will fail when using this
function arg() {
    # call with args $opts "$@"
    local opts="$1"
    shift
    #echo OPT=$opts ; exit
    # we could try to generate the short arg list from the long arg list
    # as long as there are no repeated initials
    # if opts is a long arg list, we can make an array of long arg names
#    local los=( ${opts//,/ } )
#    local sos=( $( for i in "${los[@]}" ; do echo -n "${i:0:1} " ; done ) )
#    echo "los=${los[@]}"
#    echo "sos=${sos[@]}"
#    eval $sos[1]=0.000
#    echo "${sos[1]}=$threshold" 
    
    local tmp=$( getopt -o `echo $opts | cut -d';' -f1` \
                   --long `echo $opts | cut -d';' -f2` \
	           -n "$MYNAME" -- "$@" )
    #invalid option?
    if [ $? != 0 ] ; then usage >&2 ; exit 1 ; fi

    # throw away current positional arguments and substitute them
    # with the contents of "$tmp":
    #   --  Assign any remaining arguments to the positional parameters.
    #       If there are no remaining arguments, the positional parameters
    #       are unset.
    # this will leave -- $opts at the end of the arguments, hence we need
    # to break the while loop below when we encounter a '--'
    #eval set -- "${tmp% -- *}"		# but then we won't have an exit condition for the loop
    # eval set -- "${tmp/ -- */ --}"	# this would also work
    eval set -- "${tmp}"
    #echo "tmp=$tmp" ; echo "\$#=$# \$@='$@'" ; 

#    while true ; do
#        if [ "$1" ] == "--" ] ; then break ; fi
#        idx=`get_index los $1`
#        if [ "$idx" -ne -1 ] ; then
#	    eval $los[pos]=$2
#	    shift 2
#	fi
#    done
#    exit
    
    while true ; do
        #echo "\$1=$1"
        case "$1" in
        	-\?|-h|--help)
                    usage ;
		    exit 0 ;;
		-d|--diffusion)
		    diffusion=$2 ; 
		    shift 2 ;;
		-i|--intensity)
		    intensity=$2 ; 
		    shift 2 ;;
		-f|--folder)
		    folder="$2" ; 
		    shift 2 ;;
		'--') 	# we reached the end of the arguments marked by '--'
		    shift
		    break ;;
                *) 
		    echo "ERROR: wrong option encountered ($1)!" >&2 ; 
		    usage ; 
		    exit 1 ;;
        esac
    done
}


# this function witll check the command line for conformance with a 
# specified list of arguments, call it with
#	check_args "$opts" "$@"
# uncoupling the two steps of argument processing gives us greater
# flexibility in some cases: when all arguments have a value, and
# we do not want/need to check them, get_args() alone may suffice
#	NOTE: this version requires GNU getopt, which is only
#	available in LINUX, not the trimmed-down getopt that is
#	bundled with MacOSX and FreeBSD.
function check_args() {
    local opts="$1"
    local args="$@"
    #echo "OPTS=$opts"
    #echo "ARGS=$args"
    #echo "CMD=""$@"
    #echo "ME=$MYNAME"
    local tmp
    
    tmp=$( getopt -o `echo $opts | cut -d';' -f1` \
                   --long `echo $opts | cut -d';' -f2` \
	           -n "$MYNAME" -- "$@" )
    #invalid option?
    if [ $? != 0 ] ; then usage >&2 ; exit 1 ; fi
}



# This function will process the command line and convert parameters
# to variables with their corresponding value.
# Parameters start with a '-' or a '--'
# ALL parameters must have an assigned value ('' is OK).
# Values may follow the parameter or be assigned with an '=' sign, e.g.
#	get_args -i input -out output --input=in --output=''
# This function assumes bash v4 or later
function get_args()
{
    unset extra_args
    declare -g -a extra_args		# global array
    local tmp
    local parameter
    local value
    #echo "get_args()"
    #echo "Parameters are '$*'"
    until [ -z "$1" ]
    do
        tmp="$1"
        #echo "Processing parameter: '$tmp'"
        if [ "${tmp:0:1}" = '-' ] ; then
            tmp="${tmp:1}"		# remove leading '-'
	    # check if it has a second leading '-' (supposedly a long option)
	    if [ "${tmp:0:1}" = '-' ] ; then
                tmp="${tmp:1}"		# Finish removing leading '--'
	    fi
	    #echo "$tmp ${tmp:0:1}"
	    # we'll treat the same '-' and '--' options
	    
	    # detect special cases:
	    #	options without name (- -- --=something) cannot save a
	    #   subsequent value into a 'noname' variable: set them 
	    #	separately
	    if [[ -z "$tmp" || "${tmp:0:1}" == *'=' ]] ; then
	        # add them to extra_args, remove and proceed to next
		extra_args+=( "$1" )
		shift
		continue;
	    fi
	    
	    # Now we have two possibilities:
	    # the option was of the type [-[-]]o[pt]=val or [-[-]]o[pt] val
	    # let us now tell them apart
	    if [ `expr index "$tmp" "="` -gt 1 ] ; then 
	        # it has a name and an '=', must be a [-]-opt=val
		# if we did 'eval $tmp' it would fail with embedded spaces
		parameter="${tmp%%=*}"     # Extract name (remove = and after).
		parameter="${parameter//-/_}"	# change any middle '-' to '_'
        	value="${tmp##*=}"         # Extract value (remove up to and including =).
	    else
		# there is no '=', it must be [-]-opt val
		parameter="$tmp"
	        parameter="${parameter//-/_}"	# change any middle '-' to '_'
		shift		# get next argument
		# value can be '' or start with a '-' (e.g. -1.2e3)
		value="$1"
	  fi
	  #echo "Parameter: '$parameter', value: '$value'"
	  # do the assignment
          eval $parameter="$value"
          shift
	else
	    # If a parameter does not start with '-'
	    # we will not assign this argument because we do not know
	    # which variable name to give it.
	    # But we can save it in a 'leftovers' array
	    extra_args+=( "$tmp" )
	    shift
	fi
    #echo "extra=${extra_args[@]}"
    done
    #echo "extra=${extra_args[@]}"
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



function filter_diffusion() {
    local threshold="$1"

    #	we can do it with an associative array, but also with
    read line
    ###echo "HEAD: $line"
    local declare -A H
    local i=0
    while read ln ; do H[$ln]=$((i++)); done <<< $(echo "$line" | tr ' ' '\n' ) 
    # now H (header) is an associative array that can be used to get indexes
    ###echo ${H[@]}
    
    # we have consumed and produced the header line, let's go for the rest
    while read line ; do
        l=( $line )	# convert to normal array
        d=${l[${H[diffusion]}]}
	###>&2 echo "LINE: $line"
	###echo "    diffusion ${H[diffusion]} is $d"
	if (( $(echo "${d/e/E} > ${threshold/e/E}" | bc -l) )) ; then
	    ###>&2 echo "PASSD: $line" 
	    echo "$line"		# output passing line
	###else >&2 echo "FAILD: $line" 
	fi
    done    
}



function filter_diffusion4() {
    local threshold="$1"

    #	we can do it with an associative array, but also with
    read line
    ###echo "HEAD: $line"
    echo "$line"		# output header
    local declare -A H
    local i=0
    while read ln ; do H[$ln]=$((i++)); done <<< $(echo "$line" | tr ' ' '\n' ) 
    # now H (header) is an associative array that can be used to get indexes
    ###echo ${H[@]}
    
    # we have consumed and produced the header line, let's go for the rest
    while read line ; do
        l=( $line )	# convert to normal array
        d=${l[${H[diffusion]}]}
	###>&2 echo "LINE: $line"
	###echo "    diffusion ${H[diffusion]} is $d"
	if (( $(echo "${d/e/E} > ${threshold/e/E}" | bc -l) )) ; then
	    ###>&2 echo "PASSD: $line" 
	    echo "$line"		# output passing line
	###else >&2 echo "FAILD: $line" 
	fi
    done    
}



function filter_intensity() {
    local threshold="$1"

    #	we can do it with an associative array, but also with
    read line		# get header line
    ###echo "HEAD: $line"	# 
    echo "$line"	# output header
    local declare -A H
    i=0
    while read ln ; do H[$ln]=$((i++)); done <<< $(echo $line | tr ' ' '\n' ) 
    # now H (header) is an associative array that can be used to get indexes
    ###echo "${H[@]}"
    
    # we have consumed and produced the header line, let's go for the rest
    while read line ; do
        l=( $line )	# convert to normal array
        val=${l[${H[spotCorrectedIntensity]}]}
	###>&2 echo "LINE: line"
	###echo "    spotCorrectedIntensity ${H[spotCorrectedIntensity]} is $d"
	if (( $(echo "${val/e/E} > ${threshold/e/E}" | bc -l) )) ; then
	    ###>&2 echo "PASSI: $line" 
	    echo "$line"	# output passing line
	###else >&2 echo "FAILI: $line" 
	fi
    done    
}


# this version works with bash version 3 and following
function filter_diffusion_intensity() {
    local diffusion="$1"
    local intensity="$2"
    ###echo "DIF=$diffusion INT=$intensity"

    read line		# get header line
    ###>&2 echo "HEAD: $line"	# 
    echo "$line"	# output header
    
    #	we can do it with an associative array, but also with
    declare -a H
    local i=0
    while read ln ; do H[$((i++))]="$ln"; done <<< $(echo $line | tr '\t' '\n' ) 

    #declare -A H
    #i=0
    #while read ln ; do H[$ln]=$((i++)); done <<< $(echo $line | tr ' ' '\n' ) 
    # now H (header) is an associative array that can be used to get indexes
    ###echo "${H[@]}"
    
    # we have consumed and produced the header line, let's go for the rest
    while read line ; do
        l=( $line )	  # convert to normal array
	dif_index=`get_index "${H[@]}" 'diffusion'`
        ###>&2 echo "LINE='${line}' d='$dif_index'"
        dif="${l[ dif_index ]}"
	#dif=${l[${H[diffusion]}]}
	int_index=`get_index "${H[@]}" 'spotCorrectedIntensity'`
        ###>&2 echo "LINE='${l[@]}' i='$int_index'"
	int="${l[ int_index ]}"
	#int=${l[${H[spotCorrectedIntensity]}]}
	
        ###>&2 echo "LINE: $line"
	###>&2 echo "      diffusion              H[$dif_index] is $dif"
	###>&2 echo "      spotCorrectedIntensity H[$int_index] is $int"
	###>&2 echo "      diffusion              ${H[diffusion]} is $dif"
	###>&2 echo "      spotCorrectedIntensity ${H[spotCorrectedIntensity]} is $int"
	###>&2 echo "TEST: (${dif/e/E} > ${diffusion/e/E} ) && ( ${int/e/E} > ${intensity} )"
	# for bc comparison we need exponential notation to be in caps
	dif=${dif/e/E} ; diffusion=${diffusion/e/E}
	int=${int/e/E} ; intensity=${intensity/e/E}
	if (( $(bc -l <<< "${dif} > ${diffusion} && ${int} > ${intensity}") )) ; then
	    echo "$line"	# output passing line
	###    echo "PASS: $line"
	###else echo "FAIL: $line"
	fi
    done    
}


# extract sample name from file name
function sample_from_filename() {
    local file="$1"
    
    # This only works if we are at the experiment level
    #sample=${file#./}
    #sample=${sample%%/*}
    
    local sample="$file"
    ###>&2 echo "FILE IS '$sample'"
    # remove trailing hierarchy (this fails if we are not at an exp level
    sample="${sample%/results/TrackingPackage/tracks/*}"
    ###>&2 echo "EXP FOLDER IS '$sample'"
    # remove leading hierarchy
    # since we run find for . we know all filenames start with ./
    # we have removed the trailing slash so we can now remove the
    # longest match of */
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
	out="${file%.txt}.d=$diffusion.i=$intensity.filtered.csv"
	sample=`sample_from_filename "$file"`
	>&2 echo ""
	>&2 echo "INPUT FILE IS  '$file'"
	>&2 echo "SAMPLE NAME IS '$sample'"
	>&2 echo "FILTERED FILE IS '$out'"
	# clean spaces and output the file without spaces
	# filter and output the clean file without non-complying lines
	clean_spaces "$file" \
	${comment# | filter_diffusion $diffusion #bash>=4} \
	${comment# | filter_intensity $intensity #bash>=4} \
	| filter_diffusion_intensity $diffusion $intensity \
	${comment# | tee "${out}"} \
	> "${out}"
	((i++))
	###echo $i
	###if [ $i -gt 3 ] ; then break ; fi
	###if echo "$file" | grep -q Long ; then break ; fi
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
    
    folder=`realpath "$folder"`
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
        #kind="${file%%.*}"
        #kind="${kind:(-4)}"		# extract last 4 characters
	LONG=1; SHORT=1;
	if [[ "$file" =~ "Long.d=" ]] ; then LONG=0 ; fi
	if [[ "$file" =~ "Short.d=" ]] ; then SHORT=0 ; fi
	
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
        >&2 echo "MAKING SUMMARY FOR" `basename $hierarchy`
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

# process args and set specified values (if any)
args "$opts" "$@"	
#echo "i=$intensity"
#echo "d=$diffusion"

# get bash version (bash pre-v4 does not have associative arrays)
bash_version=$(  bash --version | head -1 | sed -e "s/.*version //g" -e "s/\..*//g" )


# get_args does not require specifying the arguments, but does not check
# them for validity either
#check_args "$opts" "$@"
#get_args "$@"
#echo "extra='${extra_args[@]}'"
#: ${help?}		# exit if help is not set or null
#if [[ -v help ]] ; then echo "help is set" ; fi
#help=${help:-false}	# if not set, set help to false
#help=${help-false}	# only set help to false if already set
#echo "help=<$help>"


# go to working folder
if [ -d "$folder" ] ; then
    folder=`realpath "$folder"`
    cd "$folder"
else
    >&2 echo "'$folder' is not a directory"
    exit 1
fi

echo "Filtering log files..."
filter_log_files "$folder" "$diffusion" "$intensity"

# Create diffusion/intensity tables
# we could do it in the loop above while filtering, but that might get
# too messy, specially for all intermediate directories, so we'll do it
# separately here.

echo "Summarizing selected folder and subfolders..."
summarize_hierarchy "$folder" "$diffusion" "$intensity"

