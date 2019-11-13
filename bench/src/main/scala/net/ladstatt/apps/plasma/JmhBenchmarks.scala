package net.ladstatt.apps.plasma

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.util.Random

class JmhBenchmarks {

  @Benchmark
  @Warmup(iterations = 1, batchSize = 1000)
  @Measurement(iterations = 2, batchSize = 1000)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @Fork(1)
  def checkSinTable(): Double = {
    val angle = MathUtil.numericRange(Random.nextInt(MathUtil.numericRange.length))
    MathUtil.sin(angle.doubleValue)
    // this method was intentionally left blank.
  }
/*
  val effect = IntArrayBackedPlasmaEffect(100, 100, 1)

  @Benchmark
  @Warmup(iterations = 1, batchSize = 1000)
  @Measurement(iterations = 2, batchSize = 1000)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @Fork(1)
  def benchmarkDraw(): (Double) = {
    effect.draw(0.0)
  }
  */
}
