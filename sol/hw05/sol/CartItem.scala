package hw05.sol.copy

/**
 * Creates a new CartItem Instance
 * name - name of the item
 * cost cost of the item
 */
class CartItem (val name: String, val cost: Double){

  //Checks if the cost is less than 0 and throws respective exception
  if (cost <= 0) {
      throw new IllegalArgumentException("Cost needs to be positive");
  }
} 