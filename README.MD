# scalajs-bootstrap [![Build Status](https://travis-ci.org/Karasiq/scalajs-bootstrap.svg?branch=master)](https://travis-ci.org/Karasiq/scalajs-bootstrap) [![Scala.js](http://scala-js.org/assets/badges/scalajs-0.6.14.svg)](http://scala-js.org) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.karasiq/scalajs-bootstrap_sjs0.6_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.karasiq/scalajs-bootstrap_sjs0.6_2.12)
Reactive [Scala.js](http://www.scala-js.org/) [bootstrap](https://getbootstrap.com/) components

# How to use
Add line to your `build.sbt` (also requires `bootstrap.css` and `bootstrap.js` at runtime):
```scala
// Bootstrap 3
libraryDependencies += "com.github.karasiq" %%% "scalajs-bootstrap" % "2.3.1"

// Bootstrap 4 beta
libraryDependencies += "com.github.karasiq" %%% "scalajs-bootstrap-v4" % "2.3.1"
```

Import bootstrap context:
```scala
import com.karasiq.bootstrap.Bootstrap.default._, scalaTags.all._ 
```

# Examples
* [Project test page](https://github.com/Karasiq/scalajs-bootstrap/tree/master/test/frontend/src/main/scala/com/karasiq/bootstrap/test/frontend) 
  * Use https://karasiq.github.io/scalajs-bootstrap/ (https://karasiq.github.io/scalajs-bootstrap/index-v4)
  * Or run server with `sbt testServer/run`, then open http://127.0.0.1:9000 in your browser

# License
The MIT License (MIT)

Copyright (c) 2016 Karasiq

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
