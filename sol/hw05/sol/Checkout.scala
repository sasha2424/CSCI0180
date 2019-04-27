package hw05.sol.copy

/**
 * Creates Checkout Instance
 */
class Checkout {
  
  /**
   * Calculates the total cost of a cart with given discounts returns as Double
   * list - List of CartItems
   * -15% off nails for more than 50$ of nails
   * -$10 for 2 or more smoke alarms
   */
  def totalCost(list : List[CartItem]) : Double = {
    var nailCost = list.filter {_.name == "nails"}.foldRight(0.0)((a,b) => a.cost + b)
    val elseCost = list.foldRight(0.0)((a,b) => a.cost + b) - nailCost
    val smokeAlarmCount = list.count { _.name == "smoke alarm" }
    
    if(nailCost >= 50){
      nailCost *= .85 // scales the price of the nails
    }
    
    if(smokeAlarmCount >= 2){
      nailCost -= 10.0 // subtracts 10 from the total cost
    } 
    nailCost + elseCost
  }
} 