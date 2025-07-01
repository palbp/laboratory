import { TimeData } from './Query';

interface TimeViewProps {
  state: TimeViewState;
  onRefresh: () => void;
}

type TimeViewState = LoadingState | ErrorState | IdleState;

export function TimeView({ state, onRefresh }: TimeViewProps) {
  return (
    <>
      <h1>
        {state.type === 'loading' && <div data-testid={TEST_TAGS.LOADING}>Loading...</div>}
        {state.type === 'error' && <div data-testid={TEST_TAGS.ERROR}>Error: {state.error.message}</div>}
        {state.data && <div data-testid={TEST_TAGS.DATA}>{toDisplayText(state.data)}</div>}
      </h1>
      <div className="card">
        <button data-testid={TEST_TAGS.REFRESH_BUTTON} disabled={state.type === 'loading'} onClick={onRefresh}>Refresh</button>
      </div>
    </>
  );
}

export function toDisplayText(data: TimeData): string {
  return `${data.hours}h ${data.minutes}m ${data.seconds}s`;
}

interface LoadingState {
  type: 'loading';
  data?: TimeData | null;
}

interface ErrorState {
  type: 'error';
  error: Error;
  data?: TimeData | null;
}

interface IdleState {
  type: 'idle';
  data: TimeData;
}

export const TEST_TAGS = {
  LOADING: 'loading',
  ERROR: 'error',
  DATA: 'display',
  REFRESH_BUTTON: 'refresh-button',
}

export default TimeView;
