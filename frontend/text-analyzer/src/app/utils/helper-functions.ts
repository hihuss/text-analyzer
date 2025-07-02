import {ANALYSIS_MODE} from './constants';

export function analyzeText(text: string, mode: ANALYSIS_MODE): number {
  const vowelRegex = /[aeiou]/gi;
  const consonantRegex = /[bcdfghjklmnpqrstvwxyz]/gi;

  const regexToUse = ANALYSIS_MODE.Vowels === mode ? vowelRegex : consonantRegex;
  const matches = text.match(regexToUse);
  return matches ? matches.length : 0;
}
