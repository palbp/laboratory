// filepath: /Users/palbp/Work/Laboratory/Essays/Tech/tanstack-query/react-client/src/prep/TimeView.test.tsx
import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom'
import { describe, it, expect, vi } from 'vitest'
import { TimeView, TEST_TAGS } from './TimeView'
import { TimeData } from './Query'

const testData: TimeData = { hours: 10, minutes: 30, seconds: 45,}
const testError = new Error('Failed to fetch time')
const dummyHandler = () => {}

describe('TimeView', () => {
  it('renders loading state', () => {
    render(<TimeView state={{ data: null, error: null, isLoading: true }} onRefresh={dummyHandler} />)
    expect(screen.getByTestId(TEST_TAGS.LOADING)).toBeInTheDocument()
    expect(screen.queryByTestId(TEST_TAGS.DATA)).not.toBeInTheDocument()
    expect(screen.queryByTestId(TEST_TAGS.ERROR)).not.toBeInTheDocument()
  })

  it('renders error state', () => {
    render(<TimeView state={{ data: null, error: testError, isLoading: false }} onRefresh={dummyHandler} />)
    expect(screen.getByTestId(TEST_TAGS.ERROR)).toBeInTheDocument()
    expect(screen.queryByTestId(TEST_TAGS.DATA)).not.toBeInTheDocument()
    expect(screen.queryByTestId(TEST_TAGS.LOADING)).not.toBeInTheDocument()
  })

  it('renders data state', () => {
    render(<TimeView state={{ data: testData, error: null, isLoading: false }} onRefresh={dummyHandler} />)
    const dataView = screen.getByTestId(TEST_TAGS.DATA)
    expect(dataView).toBeInTheDocument()
    expect(dataView).toHaveTextContent(`${testData.hours}h ${testData.minutes}m ${testData.seconds}s`)
  })

  it('calls onRefresh when refresh button is clicked', () => {
    const onRefreshSpy = vi.fn()
    render(<TimeView state={{ data: testData, error: null, isLoading: false }} onRefresh={onRefreshSpy} />)
    fireEvent.click(screen.getByTestId(TEST_TAGS.REFRESH_BUTTON))
    expect(onRefreshSpy).toHaveBeenCalledTimes(1)
  })

  it('disables refresh button while loading', () => {
    render(<TimeView state={{ data: null, error: null, isLoading: true }} onRefresh={dummyHandler} />)
    expect(screen.getByTestId(TEST_TAGS.REFRESH_BUTTON)).toBeDisabled()
  })
})