import { useQuery } from '@tanstack/react-query'
import { TimeData, fetchTime } from './Query'
import TimeView from './TimeView'

export default function TimePage() {
  const { data, error, isLoading, refetch } = useQuery<TimeData>({
    queryKey: ['time'], 
    queryFn: fetchTime,
  })

  return <TimeView state={{ data, error, isLoading }} onRefresh={() => refetch()} />
}
