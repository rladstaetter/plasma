# Plasma

An old school plasma effect written in Scala. It creates a plasma effect both in JavaFX and Scala.js. 

## How to compile

Start sbt in the main directory.

In sbt shell enter

    project jfx
    
and afterwards

    run
    
you should see a window showcasing the plasma effect.

If you want to see the javascript version, enter

    project js
    
and afterwards

    fastOptJS
    
After entering this command, open plasma.html with your favorite browser.

## Thanks

This project was inspired by https://www.bidouille.org/prog/plasma and as such should be mentioned here. 

## License

See [LICENSE].

