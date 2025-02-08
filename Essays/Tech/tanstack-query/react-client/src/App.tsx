import reactLogo from './assets/react.svg'
import tanstackLogo from './assets/tanstackLogo.png'
import viteLogo from '/vite.svg'
import './App.css'
import TimePage from './time/TimePageV0'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'

const queryClient = new QueryClient()

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <TimePage />
      <Footer />
    </QueryClientProvider>
  )
}

function Footer() {
  return (
    <footer className='footer'>
      <a href="https://vite.dev" target="_blank">
        <img src={viteLogo} className="logo" alt="Vite logo" />
      </a>
      <a href="https://react.dev" target="_blank">
        <img src={reactLogo} className="logo react" alt="React logo" />
      </a>
      <a href="https://tanstack.com/query/latest" target="_blank">
        <img src={tanstackLogo} className="logo" alt="Tanstack logo" />
      </a>
    </footer>
  )
}
