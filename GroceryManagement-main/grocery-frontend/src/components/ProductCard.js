function ProductCard({ item, onAdd }) {
  return (
    <div className="card">

      <img
        src={item.image_url || item.imageUrl}
        alt={item.name}
      />

      <h4>{item.name}</h4>
      <p>₹ {item.price}</p>

      <button onClick={() => onAdd(item)}>
        🛒 Add to Cart
      </button>

    </div>
  );
}

export default ProductCard;