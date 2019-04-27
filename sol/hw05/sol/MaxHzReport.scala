package hw05.sol.copy

/**
 * Creates a new MaxHzReport Instance
 * date - the date of the report
 * freq - the maximum frequency for that day
 */
class MaxHzReport (val date : Int, val freq : Double){
  
  /**
   * Equals method to compare to other objects
   * that - Any other object
   * Returns true if the other objects is a MaxHzReport with the same values as this
   */
  override def equals(that : Any): Boolean = that match {
    case m : MaxHzReport => m.date == this.date && m.freq == this.freq
    case _ => false
  }

} 