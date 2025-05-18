class LocationsAdapter(
    private val locations: List<Location>,
    private val onItemClick: (Location) -> Unit
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.locationName)
        val address: TextView = view.findViewById(R.id.locationAddress)
        val distance: TextView = view.findViewById(R.id.locationDistance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locations[position]
        holder.name.text = location.name
        holder.address.text = location.address
        holder.distance.text = "%.1f km".format(location.distance / 1000)

        holder.itemView.setOnClickListener { onItemClick(location) }
    }

    override fun getItemCount() = locations.size
}