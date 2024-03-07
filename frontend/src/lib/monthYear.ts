import dayjs from 'dayjs'

export type MonthYear = {
  startMonthDate: dayjs.Dayjs
  firstDayOfWeek: number
  lastMonthDate: number
  monthName: string
  month: string
  year: string
}

function updateMonthYear(monthYear: MonthYear, monthIncrement: number): dayjs.Dayjs {
  return monthYear.startMonthDate.clone().add(monthIncrement, 'month')
}

export function getMonthYearDetails(initialDate: dayjs.Dayjs): MonthYear {
  const month = initialDate.format('MM')
  const year = initialDate.format('YYYY')
  const startMonthDate = dayjs(`${year}${month}01`)
  const firstDayOfWeek = Number(startMonthDate.format('d'))
  const lastMonthDate = Number(startMonthDate.clone().endOf('month').format('DD'))
  const monthName = startMonthDate.format('MMMM')
  return {
    startMonthDate,
    firstDayOfWeek,
    lastMonthDate,
    monthName,
    month,
    year,
  }
}

export function getNewMonthYear(prevData: MonthYear, monthIncrement: number): MonthYear {
  const newMonthYear = updateMonthYear(prevData, monthIncrement)
  return getMonthYearDetails(newMonthYear)
}

export function dateInPast(date: Date): boolean {
  return dayjs(date) < dayjs()
}
