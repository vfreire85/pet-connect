class FilterFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)

        view.findViewById<Button>(R.id.applyFiltersBtn).setOnClickListener {
            val filters = mapOf(
                "type" to if (view.findViewById<CheckBox>(R.id.veterinaryCheckbox).isChecked) "veterinary" else null,
                "open_now" to view.findViewById<CheckBox>(R.id.openNowCheckbox).isChecked
            )
            (activity as? LocationsListActivity)?.applyFilters(filters)
            dismiss()
        }

        return view
    }
}