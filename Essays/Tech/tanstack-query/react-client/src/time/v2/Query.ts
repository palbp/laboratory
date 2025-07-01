
export interface TimeData {
  hours: number;
  minutes: number;
  seconds: number;
}

export async function fetchTime(): Promise<TimeData> {
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
