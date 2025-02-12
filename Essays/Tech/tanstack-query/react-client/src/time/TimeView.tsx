import { TimeData } from './Query';


interface TimeViewProps {
  state: {
    data?: TimeData | null;
    error?: Error | null;
    isLoading: boolean;
  }
  onRefresh: () => void;
}

export const TEST_TAGS = {
  LOADING: 'loading',
  ERROR: 'error',
  DATA: 'display',
  REFRESH_BUTTON: 'refresh-button',
}

export function TimeView({ state: { data, error, isLoading }, onRefresh }: TimeViewProps) {
  return (
    <>
      <h1>
        {isLoading && <div data-testid={TEST_TAGS.LOADING}>Loading...</div>}
        {error && <div data-testid={TEST_TAGS.ERROR}>Error: {error.message}</div>}
        {data && <div data-testid={TEST_TAGS.DATA}>
          {data.hours.toString()}h {data.minutes.toString()}m {data.seconds.toString()}s
        </div>}
      </h1>
      <div className="card">
        <button data-testid={TEST_TAGS.REFRESH_BUTTON} onClick={onRefresh}>Refresh</button>
      </div>
    </>
  );
}

export default TimeView;
