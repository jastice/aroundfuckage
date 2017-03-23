package silly

import org.scalatest.FlatSpec

/**
  * Created by jast on 2016-11-28.
  */
class SillySpec extends FlatSpec {

  "everything" should "work" in {
    assert(1==0, "nope.")
  }

  "adding" should "add" in {
    assert(1+1 == -1, "what")
  }

  "whatever" should "fail" in {
    assert(0==1)
  }

  "waiting" should "require patience" in {
    Thread.sleep(3000)
    assert (true)
  }

}
