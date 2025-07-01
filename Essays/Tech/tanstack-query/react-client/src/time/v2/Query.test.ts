import { describe, it, expect, vi } from 'vitest'
import { fetchTime, TimeData } from './Query'

describe('fetchTime', () => {
  it('fetches time data successfully', async () => {
    const mockData: TimeData = { hours: 10, minutes: 30, seconds: 45 }
    globalThis.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(mockData),
      })
    ) as unknown as typeof fetch

    const data = await fetchTime()
    expect(data).toEqual(mockData)
  })

  it('throws an error when the response is not ok', async () => {
    globalThis.fetch = vi.fn(() =>
      Promise.resolve({
        ok: false,
      })
    ) as unknown as typeof fetch

    await expect(fetchTime()).rejects.toThrow('Failed to fetch time')
  })

  it('throws an error when the response cannot be parsed', async () => {
    globalThis.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.reject(new Error('Failed to parse response')),
      })
    ) as unknown as typeof fetch

    await expect(fetchTime()).rejects.toThrow('Failed to parse response')
  })
})