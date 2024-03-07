export const LOADING_STATUS = {
  IDLE: 'idle',
  LOADING: 'loading',
  SUCCEEDED: 'succeeded',
  FAILED: 'failed',
}
export const BASE_URL = process.env.REACT_APP_SERVER_URL
export const BASE_URL_IMAGE = `${BASE_URL}`
export const AUTH_COOKIE_MAX_AGE = 7 // 7 day
export const AUTH_COOKIE_NAME = 'user_token'

export const UPPERCASE_REGEX = new RegExp(/.*[A-Z]/)
export const NUMBER_REGEX = new RegExp(/.*\d/)
export const LENGTH_REGEX = new RegExp(/.{8,}$/)
export const SPECIAL_CHARS_REGEX = new RegExp(/.*[-’/`~!#*$@_%+=.,^&(){}[\]|;:”<>?\\]/)
export const PASSWORD_VALID_REGEX = new RegExp(
  `^(?=${[
    LENGTH_REGEX.source,
    UPPERCASE_REGEX.source,
    NUMBER_REGEX.source,
    SPECIAL_CHARS_REGEX.source,
  ].join(')(?=')}).*$`,
)
export const EMAIL_REGEX = new RegExp(/^\S+@\S+\.\S+$/)
export const DATE_FORMAT = 'YYYY-MM-DD'
