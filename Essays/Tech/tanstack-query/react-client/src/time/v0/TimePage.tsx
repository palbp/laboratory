import { useQuery } from '@tanstack/react-query'

interface TimeData {
  hours: number;
  minutes: number;
  seconds: number;
}

async function fetchTime(): Promise<TimeData> {
  const response = await fetch('/api/time')
  if (!response.ok) {
    throw new Error('Failed to fetch time')
  }
  try {
    return await response.json()
  }
  catch (error) {
    console.error('Failed to parse response: ', error)
    throw new Error('Failed to parse response')
  }
}

export default function TimePage() {
  const { data, error, isLoading, refetch } = useQuery<TimeData>({
    queryKey: ['time'], 
    queryFn: fetchTime,
    //refetchInterval: 5000,
  })

  return (
    <>
      <h1>
        {isLoading && <div>Loading...</div>}
        {error && <div>Error: {error.message}</div>}
        {data && 
          <p>{data.hours.toString()}h {data.minutes.toString()}m {data.seconds.toString()}s</p>
        }
      </h1>
      <div className="card">
        <button onClick={() => refetch()} disabled={isLoading}>Refresh</button>
      </div>
    </>
  )
}

