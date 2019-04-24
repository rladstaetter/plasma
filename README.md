# Plasma

An old school plasma effect written in Scala. It creates a plasma effect both in JavaFX and Scala.js. 

![a plasma effect](plasma-effect.png?raw=true)

See http://ladstatt.net/plasma/ for a online demo.

## How to compile

Start sbt in the main directory.

In sbt shell enter

    project jfx
    
and afterwards

    run
    
you should see a window showcasing the plasma effect using JavaFX.

If you want to see the javascript version, enter

    project js
    
and afterwards

    fastOptJS
    
After entering this command, open ./js/fastopt-plasma.html with your favorite browser - you should see the javascript demo.

There is a second option to generate a full optimized javascript version, which runs slightly faster. You can generate this version by issuing

    fullOptJS

in the sbt console. Afterwards, open ./js/fullopt-plasma.html. It will compile a little bit longer, but the difference in file size is significant.


## Thanks

This project is a reimplementation of https://www.bidouille.org/prog/plasma/ - go there if you want a more in depth explanation about the math behind this project.

## License

MIT


