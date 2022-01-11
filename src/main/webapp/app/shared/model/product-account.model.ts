import dayjs from 'dayjs';
import { IAccountDetails } from 'app/shared/model/account-details.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export interface IProductAccount {
  id?: number;
  accountNumber?: string;
  accounType?: AccountType;
  openingDate?: string;
  balance?: number | null;
  accountDetails?: IAccountDetails | null;
}

export const defaultValue: Readonly<IProductAccount> = {};
