package hw05.sol.copy


/**
 * Creates a new VoteCounting instance
 */
class VoteCounting(private val list : List[Vote]){
  
  
  /**
   * Counts the number of votes for a certain pokemon in list
   * Excludes "Electric" type pokemon
   * Returns a double representing the percentage of votes to the given pokemon
   */
   
  def candPercentage(name : String) : Double = {
    val noElectric = list.filter(_.ptype != "Electric")
    if(noElectric.length == 0)
      return 0.0
    return (noElectric.filter(_.pname == name)).length.toDouble / noElectric.length.toDouble
  }

} 