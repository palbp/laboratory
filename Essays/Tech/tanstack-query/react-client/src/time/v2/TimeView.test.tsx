// filepath: /Users/palbp/Work/Laboratory/Essays/Tech/tanstack-query/react-client/src/prep/TimeView.test.tsx
import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom'
import { describe, it, expect, vi } from 'vitest'
import { TimeView, TEST_TAGS, toDisplayText } from './TimeView'
import { TimeData } from './Query'

const testData: TimeData = { hours: 10, minutes: 30, seconds: 45,}
const testError = new Error('Failed to fetch time')
const dummyHandler = () => {}

describe('TimeView', () => {
  it('renders loading state with no data', () => {
    render(<TimeView state={{ type: 'loading' }} onRefresh={dummyHandler} />)
    expect(screen.getByTestId(TEST_TAGS.LOADING)).toBeInTheDocument()
    expect(screen.queryByTestId(TEST_TAGS.DATA)).not.toBeInTheDocument()
    expect(screen.queryByTestId(TEST_TAGS.ERROR)).not.toBeInTheDocument()
    expect(screen.getByTestId(TEST_TAGS.REFRESH_BUTTON)).toBeDisabled()
  })

  it('renders loading state with data', () => {
    render(<TimeView state={{ type: 'loading', data: testData }} onRefresh={dummyHandler} />)
    expect(screen.getByTestId(TEST_TAGS.LOADING)).toBeInTheDocument()
    const dataView = screen.getByTestId(TEST_TAGS.DATA)
    expect(dataView).toBeInTheDocument()
    expect(dataView).toHaveTextContent(toDisplayText(testData))
    expect(screen.queryByTestId(TEST_TAGS.ERROR)).not.toBeInTheDocument()
    expect(screen.getByTestId(TEST_TAGS.REFRESH_BUTTON)).toBeDisabled()
  })

  it('renders error state with no data', () => {
    render(<TimeView state={{ type: 'error', error: testError }} onRefresh={dummyHandler} />)
    expect(screen.getByTestId(TEST_TAGS.ERROR)).toBeInTheDocument()
    expect(screen.queryByTestId(TEST_TAGS.DATA)).not.toBeInTheDocument()
    expect(screen.queryByTestId(TEST_TAGS.LOADING)).not.toBeInTheDocument()
    expect(screen.getByTestId(TEST_TAGS.REFRESH_BUTTON)).toBeEnabled()
  })

  it('renders error state with data', () => {
    render(<TimeView state={{ type: 'error', error: testError, data: testData }} onRefresh={dummyHandler} />)
    expect(screen.getByTestId(TEST_TAGS.ERROR)).toBeInTheDocument()
    const dataView = screen.getByTestId(TEST_TAGS.DATA)
    expect(dataView).toBeInTheDocument()
    expect(dataView).toHaveTextContent(toDisplayText(testData))
    expect(screen.queryByTestId(TEST_TAGS.LOADING)).not.toBeInTheDocument()
    expect(screen.getByTestId(TEST_TAGS.REFRESH_BUTTON)).toBeEnabled()
  })

  it('renders idle state', () => {
    render(<TimeView state={{ type: 'idle', data: testData }} onRefresh={dummyHandler} />)
    const dataView = screen.getByTestId(TEST_TAGS.DATA)
    expect(dataView).toBeInTheDocument()
    expect(dataView).toHaveTextContent(toDisplayText(testData))
    expect(dataView).toHaveTextContent(`${testData.hours}h ${testData.minutes}m ${testData.seconds}s`)
  })

  it('calls onRefresh when refresh button is clicked', () => {
    const onRefreshSpy = vi.fn()
    render(<TimeView state={{ type: 'idle', data: testData }} onRefresh={onRefreshSpy} />)
    fireEvent.click(screen.getByTestId(TEST_TAGS.REFRESH_BUTTON))
    expect(onRefreshSpy).toHaveBeenCalledTimes(1)
  })
})