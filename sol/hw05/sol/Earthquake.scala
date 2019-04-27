package hw05.sol.copy


/**
 * Creates a new Earthquake Instance
 */
class Earthquake {

  /**
   * Creates a list of MaxHzReports for each day of the given month from the given list of data doubles.
   */
  def dailyMaxForMonth(list: List[Double], month: Int): List[MaxHzReport] = {
    val removeBefore = list.dropWhile(x => if (x > 10000000) (x.toInt / 100) % 100 != month else true)
    val monthData = removeBefore.takeWhile { x => if (x > 10000000) (x.toInt / 100) % 100 == month else true }
    
    if(monthData.isEmpty){
      return List[MaxHzReport]()
    }
    //Parse the Month Data
    //There is no good way to do this without a for loop
    //This is also a bit easier to read
    var r = List[MaxHzReport]()

    var date = monthData.head.toInt 
    var max = 0.0
    for (x <- monthData) {
      if (x > 10000000) {
        r = new MaxHzReport(date, max) :: r
        date = x.toInt
        max = 0.0
      } else {
        max = Math.max(max, x)
      }
    }
    
    //add the last value, reverse the list, and drop the duplicate first date
    (new MaxHzReport(date, max) :: r).reverse.tail

    
  }

}