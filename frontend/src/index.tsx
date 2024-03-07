import { Provider } from 'react-redux'
import { createRoot } from 'react-dom/client'
import reportWebVitals from './reportWebVitals'
import { store } from './state/store'
import Routes from './routes'

const root = createRoot(document.getElementById('root') as HTMLElement)
root.render(
  <Provider store={store}>
    <Routes />
  </Provider>,
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
