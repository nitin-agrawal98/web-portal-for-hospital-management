export interface Doctor {
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
  qualifications: string[];
  specialization: string;
}
