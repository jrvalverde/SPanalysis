This directory contains the sources from GitHub.

There were at least two missing files, SPTBatch_.java and STScatterPlot.java.

Decompiling the classes provided in the jar file at GitHub (files *.java,00*)
allowed code compilation, but the resulting plugin was defective (in the best
situation it hung at 33% run).

Two versions of the files were provided by the original author, but these
versions were inconsistent with other files in the source code (at least
with ComputeMSD.java)

Inspection of the running compiled plugin version available at CNB, which lacks
the source code, after decompilation shows that
	- SPTBatch_.java (decompiled) is consistent with ComputeMSD.java
	(decompiled)
	- ComputeMSD.java has no constructor and lacks variables used in
	the provided SPTBatch_.java that are not needed by the decompiled
	SPTBatch_.java

Other issues:
	The Jar files and Plugin jars are inconsistently divided in the
	corresponding directories which leads to anomalous Fiji/ImageJ
	menus.

Thus:

	WORK CLEANING EVERYTHING BEFORE EACH COMPILATION CYCLE
	
	HAVE A FIJI/IMAGEJ DIRECTORY IN PRISTINE STATE
	
	HAVE extra/{jars|plugins} FOLDERS AND POPULATE THEM AS NEEDED
	TO ENSURE WE KNOW EXACTLY WHICH JAR FILES DO ACTUALLY NEED TO
	BE INCLUDED AS ADD-ONS WITH THE PLUGIN, AND WHERE SHOULD THEY
	BE ACTUALLY INSTALLED IN THE IMAGEJ/FIJI FOLDERS.

	DO NOT USE THIS CODE
	
	WORK WITH THE CNB VERSION INSTEAD
	
	ONLY USE THIS CODE AS REFERENCE FOR FIXING THE CODE IN CNB
