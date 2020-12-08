import {Status} from '../status.enum';

export interface Patient {
  id: number;
  loginName: string;
  passcode: string;
  firstName: string;
  lastName: string;
  dob: Date;
  city: string;
  state: string;
  country: string;
  pinCode: number;
  contact: number;
  email: string;
  question: string;
  pastIllness: string;
  status: Status;
}
