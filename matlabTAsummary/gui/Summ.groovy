/**
 *	Summ - a GUI interface for the matlabTAsummary bash script
 *
 *    This program will open a dialog box where it will ask the user
 * for the folder containing all the experimental results to summarize
 * and the desired thresholds (diffusion and intensity), and then if
 * Cancel is selected do nothing and exit, or if Run is chosen, it will
 * determine if it is being run from a Jar file or as a Groovy script.
 * If it is run from a jar file, it will extract the bash script and
 * run it with the options chosen by the user, otherwise, it will
 * assume that the script has been installed in  ~/bin and run it
 * with the options chosen by the user. After running the script, it
 * exits.
 *
 * @see https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
 * @see https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 *
 * @author	Jose R. Valverde <jrvalverde@cnb.csic.es>
 * @license	EU-GPL
 * @version	%I%, %G%
 * @since	2024-09-24
 * 
 */
//package es.csic.cnb.scs.matlabTAsumm

import groovy.swing.SwingBuilder 
import javax.swing.JFileChooser
import groovy.beans.Bindable 
import static javax.swing.JFrame.EXIT_ON_CLOSE 
import java.awt.*
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.text.*

// to detect executable availability
import java.io.File;
//import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
// alt
import java.util.regex.Pattern
import java.util.stream.Stream

import static java.io.File.pathSeparator;
import static java.nio.file.Files.isExecutable;
import static java.lang.System.getenv;
import static java.util.regex.Pattern.quote;


@Bindable
class CmdOptions { 
    String folder, diffusion, intensity
    
    // a function to check if an executable exists (to determine whether
    // we have zsh or bash
    private static boolean cmdExists(String cmdFileName)
    {
	// get PATH environment variable
	String sysPathEnv = System.getenv("PATH");

	// split into component directories
	String[] sysDirectories = sysPathEnv.split(File.pathSeparator);
	for (String cmdDir : sysDirectories)
	{
            try
            {
		// convert directory to path
        	Path cmdDirectoryPath = Path.of(cmdDir);
		//Path cmdDirectoryPath = Paths.get(cmdDir);
		// if the directory exists
        	if (Files.exists(cmdDirectoryPath))
        	{
	            // search for command in the directory
                    Path cmdFilePath = cmdDirectoryPath.resolve(cmdFileName);
		    // if it exists and is executable
                    if (Files.isExecutable(cmdFilePath))
                    {
                	return true;
                    }
		    // otherwise try a new directory from the path
        	}
            } catch (Exception exception)
            {
        	exception.printStackTrace();
            }
	}

	return false;
    }

    
    // this is shorter and more idiomatic, equivalent to the
    // code above: find if there is any command named 'exec'
    // in any of the path directories
    private static boolean commandExists(String exec)
    {
	return System.getenv("PATH")
	      .split(Pattern.quote(File.pathSeparator))
	      .any{p -> Files.exists(Paths.get(p).resolve(exec))};
    }

    // we use the toString() attribute to generate the command as a
    // string, so we can later call the string attribute execute()
    // to run it.
    //
    // For later: note that we could extract one script from the jar
    // file and execute it with unzip -p (extract to stdout) and piping
    // the script into bash (telling it to get arguments (-s) from file
    // stdin (-).
    //	unzip -p JARFILE SCRIPTFILE | bash -s - arguments
    // but we would need to know the name of our jar bundle
    // maybe with 
    //	  File currentJavaJarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());   
    //	  String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
    //	  String currentRootDirectoryPath = currentJavaJarFilePath.replace(currentJavaJarFile.getName(), "");
    //
    // or Path:
    //	  Path path = Paths.get(Test.class.getProtectionDomain().getCodeSource().getLocation().toURI());
    //
    // or use ClassLoader.getResource to find the URL of our current class:
    //	  ClassLoader loader = Test.class.getClassLoader();
    //	  System.out.println(loader.getResource("foo/Test.class"));
    // 
    // if the jar file is obfuscated, one can get the class name with
    //	  Test.class.getName()
    // 
    // or
    // 	  CodeSource codeSource = YourMainClass.class.getProtectionDomain().getCodeSource();
    // 	  File jarFile = new File(codeSource.getLocation().toURI().getPath());
    // and to get the parten directory use
    // 	  String jarDir = jarFile.getParentFile().getPath();
    // 
    // or
    //	  CmdOptions.class.getClass().protectionDomain.codeSource.location
    // 
    // or
    //	  return new File(CmdOptions.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
    //		the toURI() step is vital to avoid problems with special chars
    //		this returns the path including the name of the jar file
    //		if used on a class that is in an external jar, this will
    //		give the path of the external jar file
    // or
    //   String path = Test.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    // 	  String decodedPath = URLDecoder.decode(path, "UTF-8");
    //		this should solve problems with spaces and special chars
    //
    // the basic approach is to convert the class to a url and the url to a file
    // there are two ways to get the url:
    //    URL url = Bar.class.getProtectionDomain().getCodeSource().getLocation();
    // and
    //	  URL url = Bar.class.getResource(Bar.class.getSimpleName() + ".class");
    //
    // The getProtectionDomain approach yields the base location of the class
    // (e.g., the containing JAR file). However, it is possible that the Java
    // runtime's security policy will throw SecurityException when calling
    // getProtectionDomain(), so if your application needs to run in a variety of
    // environments, it is best to test in all of them.
    // 
    // The getResource approach yields the full URL resource path of the class, from
    // which you will need to perform additional string manipulation. It may be a
    // file: path, but it could also be jar:file: or even something nastier like
    // bundleresource://346.fwk2106232034:4/foo/Bar.class when executing within an
    // OSGi framework. Conversely, the getProtectionDomain approach correctly yields
    // a file: URL even from within OSGi.
    // 
    // Note that both getResource("") and getResource(".") failed in my tests, when
    // the class resided within a JAR file; both invocations returned null. So I
    // recommend the #2 invocation shown above instead, as it seems safer.
    //
    // Either way, once you have a URL, the next step is convert to a File. This
    // is its own challenge; see Kohsuke Kawaguchi's blog post about it for full
    // details, but in short, you can use new File(url.toURI()) as long as the URL
    // is completely well-formed.
    // 
    // Lastly, I would highly discourage using URLDecoder. Some characters of the
    // URL, : and / in particular, are not valid URL-encoded characters. From the
    // URLDecoder Javadoc:
    // 
    // It is assumed that all characters in the encoded string are one of the
    // following: "a" through "z", "A" through "Z", "0" through "9", and "-", "_",
    // ".", and "*". The character "%" is allowed but is interpreted as the start of
    // a special escaped sequence.
    // ...
    // There are two possible ways in which this decoder could deal with illegal
    // strings. It could either leave illegal characters alone or it could throw an
    // IllegalArgumentException. Which approach the decoder takes is left to the
    // implementation.
    //
    // In practice, URLDecoder generally does not throw IllegalArgumentException as
    // threatened above. And if your file path has spaces encoded as %20, this
    // approach may appear to work. However, if your file path has other
    // non-alphameric characters such as + you will have problems with URLDecoder
    // mangling your file path.
    // 
    // To achieve these steps, you might have methods like the ones at 
    // the bottom (which can be found in the SciJava Common library at
    // org.scijava.util.ClassUtils
    // org.scijava.util.FileUtils
    //
    //String toString() { "bash ~/bin/matlabTAsummary.bash --folder='$folder' --diffusion='$diffusion' --intensity='$intensity'" }
    //
    String myjar = new File(CmdOptions
    				.class
				.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.toURI()
			).getCanonicalPath()
			// NOTE: getCanonicalPath may throw an exception
			//).getAbsolutePath()
			//).getPath()
    String toString() {
        // this ensures we get valid values by checking that diffusion and 
	// intensity are numbers
	// (they should be valid as we use formatted entry fields)
	try {
	    intensity as Double
	    diffusion as Double
	} catch (Exception ex) {
	    // This works well because we do it directly
	    JOptionPane.showMessageDialog(new JFrame(),
		ex.toString(), 
		"Error",
                JOptionPane.ERROR_MESSAGE);
	    // this one runs in line, so we block until
	    // OK is clicked, and then continue to:
	    System.exit(1)
	}

/*	// direct approach using bash (fails on modern Macs)
	//println("myjar=<$myjar>")
	// check extension is '.jar'
	if (myjar =~ /\.jar$/ ) {
	    // if we were in a jar file, extract script and run it
            "unzip -p " + myjar + " matlabTAsummary.bash " +
            "| bash -s - -f '$folder' -d '$diffusion' -i '$intensity'" 
	} else {
	    // otherwise, expect the script installed as ~/bin/matlabTAsummary.bash
	    "bash ~/bin/matlabTAsummary.bash -f '$folder' -d '$diffusion' -i '$intensity'"
	}
*/
	def String shell;
	if (cmdExists('zsh')) {
	    shell='zsh'
	} else if (cmdExists('bash')) {
	    shell='bash'
	}
	if (myjar =~ /\.jar$/ ) {
	    // if we were in a jar file, we can extract the script and run it
            "unzip -p " + myjar + " matlabTAsummary.$shell " +
            "| $shell -s - -f '$folder' -d '$diffusion' -i '$intensity'" 
	} else {
	    // otherwise, expect the script installed as ~/bin/matlabTAsummary.$shell
	    "$shell ~/bin/matlabTAsummary.$shell -f '$folder' -d '$diffusion' -i '$intensity'"
	}

    }
}

    
class Summarizer {

    // show a dialog with a scrollable textArea
    static void showTextDialog( String text) {
	def BL = new BorderLayout()
	def swing = new SwingBuilder()
	def frame = swing.frame(title: 'Results', 
                	    size: [600,300], 
                	    show: true,
                	    locationRelativeTo: null,
                	    layout: new BorderLayout(vgap: 5)) {
	    scrollPane(constraints:BL.CENTER) {
        	textArea(text: text,
                	 editable: false,
                	 lineWrap:false, 
	        	 wrapStyleWord:true
        	)
	    }  
	    panel(constraints:BL.SOUTH) {
        	button(text: "OK", 
		       defaultButton: true,
		       actionPerformed: { System.exit(0) } )
	    }
	}
	//frame.getRootPane().setDefaultButton(okButton)
	//frame.show()

    }


  static void main(String[] args) {
  /*
    def cm = "ls zLog not.txt"
    def s_out=new StringBuilder(), s_err=new StringBuilder()
    def pro = cm.execute()
    println "Waiting..."
    pro.waitForProcessOutput(s_out, s_err)
    println "Done"
    String oute = "[OUTPUT]\n" + s_out.toString() + "\n\n[NOTICES]\n" + s_err.toString()
    //println oute;
    showTextDialog(oute.toString())
    println "\n\nDialog closed"
    return
  */
    def cmd = "ls"

    def command = new CmdOptions(folder: new java.io.File('.').getAbsolutePath(), diffusion: '0', intensity: '0')

    def swingBuilder = new SwingBuilder()
    swingBuilder.edt {  // edt method makes sure UI is build on Event Dispatch Thread.

	// we want these globally shared across this procedure
	def directory;
	def browse;
	def stout = new StringBuilder()
	def sterr = new StringBuilder()
	def String outerr;

	def setDirectory = { String s -> directory.setText(s) }

	def getDirectory = {
	    // create a file chooser
	    JFileChooser chooser = new JFileChooser() 

	    // start looking for the directory from the current one
	    // chooser.setCurrentDirectory(new java.io.File(".").getCanonicalPath())
	    chooser.setCurrentDirectory(new java.io.File("."))
	    chooser.setDialogTitle("Select folder to process")

	    // filter to show only directories
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)

	    // get the user action
	    int returnVal = chooser.showOpenDialog()

	    // if the user selects a directory
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       // get the directory and start your logic
	       File selectedDirectory = chooser.getSelectedFile()
	       /* examples:
	       // sample print directory path
	       println('Selected directory: ' + selectedDirectory.getAbsolutePath())
	       // sample print all files inside selected directory
	       selectedDirectory.listFiles().each{ file ->
        	    println(file.getAbsolutePath())
	       */
	       setDirectory( selectedDirectory.getAbsolutePath() )
	    }
	}
	
	


	def NumberFormat nf = NumberFormat.getInstance()
	frame(title: 'Summ', 
	      size: [500, 200],
              show: true, 
	      locationRelativeTo: null,
              defaultCloseOperation: EXIT_ON_CLOSE) {
            borderLayout(vgap: 5)

            panel(constraints: BorderLayout.CENTER,
                    border: compoundBorder([emptyBorder(10), titledBorder('Enter your options:')])) {
        	tableLayout {
                    tr {
                	td {
                            label 'intensity:'
                	}
                	td {
                            //textField id: 'intensityField', columns: 20, text: command.intensity
                            formattedTextField format: nf, id: 'intensityField', columns: 20, text: command.intensity
                	}
                    }
                    tr {
                	td {
                            label 'Diffusion:'
                	}
                	td {
                            //textField id: 'diffusionField', columns: 20, text: command.diffusion
			    formattedTextField format: nf, id: 'diffusionField', columns: 20, text: command.diffusion
                	}
                    }
                    tr {
                	td {
                            label 'Folder:'  // text property is default, so it is implicit.
                	}
                	td {
                            //textField command.folder, id: 'folderField', columns: 20
                            directory = textField(
				id: 'folderField', 
				columns: 25, 
				text: new java.io.File(".").getAbsolutePath()
				)
			}
			td {
			   //button text: 'Browse', actionPerformed: {
                	   //    println command
                	   //}
		            browse = button(
				id: 'Browse', 
				text: 'Browse', 
				actionPerformed: {	getDirectory() } 
			    )
			}

                    }
        	}

            }

            panel(constraints: BorderLayout.SOUTH) {
		tableLayout(cellpadding: 5) {
                    tr {
                	td {
        		    button text: 'Run', 
			        defaultButton: true,
			        actionPerformed: {
                		thecmd = command.toString()
				println thecmd;
				// since we can get a pipeline command, we need to
				// have it passed to a shell that can interpret it
				//   thecmd="bash ./zygCMD.sh --folder='/mnt/jr/work/mmellado/TrackAnalyzer/test_cnb_2/.' --diffusion='0' --intensity='0'"
				//   thecmd="bash ~/bin/matlabTAsummary.bash --folder='/mnt/jr/work/mmellado/TrackAnalyzer/test_cnb_2/.' --diffusion='0' --intensity='0'"
				//   thecnd="unzip -p gui/Summ.jar matlabTAsummary.bash | bash -s - --folder='/mnt/jr/work/mmellado/TrackAnalyzer/test_cnb_2/.' --diffusion='0' --intensity='0'"
				cmd = ['/bin/sh',  '-c',  thecmd ]
				println cmd;
				// use Process String.execute(envp, File dir) to run it
				// just in case we'll run it in the destination folder
				//    cmd.execute(null, new java.io.File(command.folder))
				// better if we print the output
				//    println cmd.execute(null, new java.io.File(command.folder)).text
				// and better if we check for exceptions
				try  {
				    //cmd="ls " + command.folder + " ; echo OK"
				    //println cmd; println command.folder
				    
				    // this only shows StdOut, not StdErr
				    //println cmd.execute(null, new java.io.File(command.folder)).text
				    
				    def proc = cmd.execute(null, new java.io.File(command.folder))
				    println "Waiting..."
				    proc.waitForProcessOutput(stout, sterr)
				    println "Done"
				    outerr = "[OUTPUT]\n" + stout.toString() \
				           + "\n\n[NOTICES]\n" + sterr.toString()
				    /*
				    def proc = cmd.execute(null, new java.io.File(command.folder))
				    def String stout = println proc.in.text
				    def String sterr = println proc.err.text
				    def String outerr = "[OUTPUT]\n" + stout + "[NOTICES]\n" + sterr
				    println outerr
				    */
				    /*
				    //def String output = cmd.execute(null, new java.io.File(command.folder)).text
				    //println output;
				    //showCmdStdOutErr;
				    */
				    /*
				    def proc = cmd.execute(null, new java.io.File(command.folder))
				    // get out on the fly
				    //proc.consumeProcessOutput(stout, sterr)
				    //showCmdStdOutStdErr
				    //proc.waitForOrKill(100000)		// wait up to a timeout in milliseconds
				    def stout=proc.text
				    def sterr=proc.err.text
				    // wait for all output to be consumed
				    proc.waitForProcessOutput(stout, sterr)
				    def errorCode = proc.exitValue()
				    showCmdStdOutStdErr
				    */
				    
				} catch (Exception ex) {
			            //println("Caught exception " + ex.toString())
        			    //println(ex.getMessage());
        			    //println(ex.getStackTrace());  
				    // this doesn't work because swing
				    // runs in a thread that is stopped on exceptions
				    //   def msg = ex.toString() + ex.getMessage().toString()
				    //   showTextDialog(msg)
				    // This works well because we do it directly
				    JOptionPane.showMessageDialog(new JFrame(),
				        ex.toString(), 
					"Error",
                                        JOptionPane.ERROR_MESSAGE);
				    // this one runs in line, so we block until
				    // OK is clicked, and then continue to:
				    System.exit(1)
				}
                                println outerr;
				//println "\n\nCreating dialog"
				showTextDialog(outerr)
				//println "\n\nDialog closed"
				// ShowTextDialog runs in a separate thread, if
				// we exit here, it will be killed, so we must
				// exit from ShowTextDialog
				//System.exit(0)
        		    }
	        	}
			td {
		            button text: 'Cancel', actionPerformed: { System.exit(0) } 
			}
		    }
        	}
            }

            // Binding of textfield's to command object.
            bean command,
        	folder: bind { folderField.text },
        	diffusion: bind { diffusionField.text },
        	intensity: bind { intensityField.text }
	} 
    /*
	doOutside {		# run in parallel with the user interface
            for (i in 1..10) {
        	sleep 1000
        	edt { setDirectory(String.valueOf(i)) } # send back commands to the UI
            }
	}
    */
    }
  }
}


//------------------- getting my jar file ------------------------------
// 
// /**
//  * Gets the base location of the given class.
//  * <p>
//  * If the class is directly on the file system (e.g.,
//  * "/path/to/my/package/MyClass.class") then it will return the base directory
//  * (e.g., "file:/path/to").
//  * </p>
//  * <p>
//  * If the class is within a JAR file (e.g.,
//  * "/path/to/my-jar.jar!/my/package/MyClass.class") then it will return the
//  * path to the JAR (e.g., "file:/path/to/my-jar.jar").
//  * </p>
//  *
//  * @param c The class whose location is desired.
//  * @see FileUtils#urlToFile(URL) to convert the result to a {@link File}.
//  */
// public static URL getLocation(final Class<?> c) {
//     if (c == null) return null; // could not load the class
// 
//     // try the easy way first
//     try {
//         final URL codeSourceLocation =
//             c.getProtectionDomain().getCodeSource().getLocation();
//         if (codeSourceLocation != null) return codeSourceLocation;
//     }
//     catch (final SecurityException e) {
//         // NB: Cannot access protection domain.
//     }
//     catch (final NullPointerException e) {
//         // NB: Protection domain or code source is null.
//     }
// 
//     // NB: The easy way failed, so we try the hard way. We ask for the class
//     // itself as a resource, then strip the class's path from the URL string,
//     // leaving the base path.
// 
//     // get the class's raw resource path
//     final URL classResource = c.getResource(c.getSimpleName() + ".class");
//     if (classResource == null) return null; // cannot find class resource
// 
//     final String url = classResource.toString();
//     final String suffix = c.getCanonicalName().replace('.', '/') + ".class";
//     if (!url.endsWith(suffix)) return null; // weird URL
// 
//     // strip the class's path from the URL string
//     final String base = url.substring(0, url.length() - suffix.length());
// 
//     String path = base;
// 
//     // remove the "jar:" prefix and "!/" suffix, if present
//     if (path.startsWith("jar:")) path = path.substring(4, path.length() - 2);
// 
//     try {
//         return new URL(path);
//     }
//     catch (final MalformedURLException e) {
//         e.printStackTrace();
//         return null;
//     }
// } 
// 
// 
// /**
//  * Converts the given {@link URL} to its corresponding {@link File}.
//  * <p>
//  * This method is similar to calling {@code new File(url.toURI())} except that
//  * it also handles "jar:file:" URLs, returning the path to the JAR file.
//  * </p>
//  * 
//  * @param url The URL to convert.
//  * @return A file path suitable for use with e.g. {@link FileInputStream}
//  * @throws IllegalArgumentException if the URL does not correspond to a file.
//  */
// public static File urlToFile(final URL url) {
//     return url == null ? null : urlToFile(url.toString());
// }
// 
// /**
//  * Converts the given URL string to its corresponding {@link File}.
//  * 
//  * @param url The URL to convert.
//  * @return A file path suitable for use with e.g. {@link FileInputStream}
//  * @throws IllegalArgumentException if the URL does not correspond to a file.
//  */
// public static File urlToFile(final String url) {
//     String path = url;
//     if (path.startsWith("jar:")) {
//         // remove "jar:" prefix and "!/" suffix
//         final int index = path.indexOf("!/");
//         path = path.substring(4, index);
//     }
//     try {
//         if (PlatformUtils.isWindows() && path.matches("file:[A-Za-z]:.*")) {
//             path = "file:/" + path.substring(5);
//         }
//         return new File(new URL(path).toURI());
//     }
//     catch (final MalformedURLException e) {
//         // NB: URL is not completely well-formed.
//     }
//     catch (final URISyntaxException e) {
//         // NB: URL is not completely well-formed.
//     }
//     if (path.startsWith("file:")) {
//         // pass through the URL as-is, minus "file:" prefix
//         path = path.substring(5);
//         return new File(path);
//     }
//     throw new IllegalArgumentException("Invalid URL: " + url);
// }
// 
// // This one works on Linux, Mac, WIndows
// public static String getJarContainingFolder(Class aclass) throws Exception {
//   CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();
// 
//   File jarFile;
// 
//   if (codeSource.getLocation() != null) {
//     jarFile = new File(codeSource.getLocation().toURI());
//   }
//   else {
//     String path = aclass.getResource(aclass.getSimpleName() + ".class").getPath();
//     String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
//     jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
//     jarFile = new File(jarFilePath);
//   }
//   return jarFile.getParentFile().getAbsolutePath();
// }
// 
// // note: we can get the path of our current working directory
// // with new File(".").getCanonicalPath()
// 
//--------------------------------------------------------------------


Summarizer.main()

