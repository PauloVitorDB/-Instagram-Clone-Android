package co.pvitor.instagram.common.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<P: BasePresenter, B>(
    @LayoutRes private val layoutId: Int,
    private val bind: (View) -> B
): Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    abstract var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setupPresenter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.clear()

        getMenu()?.let {
            inflater.inflate(it, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    @MenuRes
    open fun getMenu(): Int? {
        return null
    }

    abstract fun setupPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        _binding = bind(view)

        setupOnViewCreated()

    }

    abstract fun setupOnViewCreated()

    override fun onDestroy() {
        presenter.onDestroy()
        _binding = null
        super.onDestroy()
    }


}