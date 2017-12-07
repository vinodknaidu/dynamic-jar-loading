# dynamic-jar-loading
Dynamically loading the jars that are internally dependent on additional jars.

Let us say we are dynamically loading a jar, say x.jar. There are a few classes in x.jar that have a composition which is depending on another jar, say y.jar. The class in x.jar will fail if y.jar is not available. We have to dynamically load the y.jar as well.

Suppose, few classes in y.jar is is depending on some other jar, say z.jar, then those classes in y.jar will fail if z.jar is not available leading to failure of x.jar as well.

Place all the jar along will its dependent jars in the same folder(let us name this folder 'jars'). This is a maven project and place the 'jars' folder under resources.
