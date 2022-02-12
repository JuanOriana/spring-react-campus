import type { Config } from '@jest/types'

const config: Config.InitialOptions = {
  verbose: true,
  transform: {
    '^.+\\.tsx?$': 'ts-jest',
  },
  rootDir: 'src',
  testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
}
export default config
