{showCart && (
  <div className="cart-overlay" onClick={() => setShowCart(false)}>

    <div className="cart-panel" onClick={(e) => e.stopPropagation()}>

      <h3>🛒 Your Cart</h3>

      {cart.length === 0 ? (
        <p>Cart is empty</p>
      ) : (
        cart.map((item) => (
          <div className="cart-item" key={item.id}>
            <span>{item.name}</span>

            <div>
              <button onClick={() => decrease(item.id)}>-</button>
              {item.qty}
              <button onClick={() => increase(item.id)}>+</button>
            </div>

            <span>₹{item.price * item.qty}</span>
          </div>
        ))
      )}

      <h3>Total: ₹{total}</h3>

      <button
        onClick={() => {
          alert("Order placed 🎉");
          setCart([]);
          setShowCart(false);
        }}
      >
        Checkout
      </button>

      <button onClick={() => setShowCart(false)}>
        Close
      </button>

    </div>
  </div>
)}