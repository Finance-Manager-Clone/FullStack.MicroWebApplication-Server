import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';
import { IProductAccount } from 'app/shared/model/product-account.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface ITransaction {
  id?: number;
  transactionType?: TransactionType;
  amount?: number;
  time?: string;
  currency?: Currency | null;
  category?: ICategory | null;
  from?: IProductAccount | null;
  to?: IProductAccount | null;
}

export const defaultValue: Readonly<ITransaction> = {};
