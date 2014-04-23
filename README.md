<img src="http://scala-miniboxing.org/mbox2-thumbnail.png" alt="Miniboxing Logo" align="right">
#Miniboxing Benchmarks

This project regroups a bunch of benchmarks that are meant to show the performance of the miniboxing plugin versus specialization and genericity.

For more details on the miniboxing plugin and how to use it, please visit [scala-miniboxing.org](http://scala-miniboxing.org).

Performance example of a custom class `Vector3D[T]` measuring method `+`:
```
[info] ::Benchmark Vector3D.+::
[info] Parameters(Generic Vector 3D):     59.272875

[info] ::Benchmark Vector3D.+::
[info] Parameters(Specialized Vector 3D): 20.732548

[info] ::Benchmark Vector3D.+::
[info] Parameters(Miniboxed Vector 3D):   10.26064
```


#### Try miniboxing on your own! [Read more](http://scala-miniboxing.org) or [use miniboxing with your project](https://github.com/miniboxing/miniboxing-example).
Or have a look at an [array reverse example](https://gist.github.com/VladUreche/6891789).
