import {ANALYSIS_MODE} from './constants';

export function analyzeText(text: string, mode: ANALYSIS_MODE): Record<string, number> {
  const vowelRegex = /[aeiou]/gi;
  const consonantRegex = /[bcdfghjklmnpqrstvwxyz]/gi;

  const regexToUse = ANALYSIS_MODE.Vowels === mode ? vowelRegex : consonantRegex;
  const matches = text.match(regexToUse);

  const letterCounts: Record<string, number> = {};

  if (matches) {
    for (const letter of matches) {
      const lowerLetter = letter.toLowerCase();
      if (letterCounts[lowerLetter]) {
        letterCounts[lowerLetter]++;
      } else
        letterCounts[lowerLetter] = 1;
    }
  }

  return letterCounts;
}
