/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package speech_recognition_using_sphinx4;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * A simple HelloWorld demo showing a simple speech application 
 * built using Sphinx-4. This application uses the Sphinx-4 endpointer,
 * which automatically segments incoming audio into utterances and silences.
 */
public class Speech_Recognition_using_Sphinx4 {

    /**
     * Main method for running the HelloWorld demo.
     */
    public static void main(String[] args) {
        
    
        
        try {
                ConfigurationManager cm;
            if (args.length > 0) {
               cm = new ConfigurationManager(args[0]);
            } else {
                cm = new  ConfigurationManager(Speech_Recognition_using_Sphinx4.class.getResource( "helloworld.config.xml"));
            }

            System.out.println("Loading...");

            //ConfigurationManager cm = new ConfigurationManager(url);
           

	    Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
	    Microphone microphone = (Microphone) cm.lookup("microphone");


            /* allocate the resource necessary for the recognizer */
            recognizer.allocate();

            /* the microphone will keep recording until the program exits */
	    if (microphone.startRecording()) {

		System.out.println
		    ("Say: (Good morning | Hello Bhiksha | Evandro | Paul | Philip | Rita | Will )");

		while (true) {
		    System.out.println
			("Start speaking. Press Ctrl-C to quit.\n");

                    /*
                     * This method will return when the end of speech
                     * is reached. Note that the endpointer will determine
                     * the end of speech.
                     */ 
		    Result result = recognizer.recognize();
		    
		    if (result != null) {
			String resultText = result.getBestFinalResultNoFiller();
			System.out.println("You said: " + resultText + "\n");
		    } else {
			System.out.println("I can't hear what you said.\n");
		    }
		}
	    } else {
		System.out.println("Cannot start microphone.");
		recognizer.deallocate();
		System.exit(1);
	    }
        } catch (PropertyException e) {
            System.err.println("Problem configuring HelloWorld: " + e);
            e.printStackTrace();
        }
    }
}
