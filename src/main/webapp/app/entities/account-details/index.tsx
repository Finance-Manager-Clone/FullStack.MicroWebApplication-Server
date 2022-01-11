import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AccountDetails from './account-details';
import AccountDetailsDetail from './account-details-detail';
import AccountDetailsUpdate from './account-details-update';
import AccountDetailsDeleteDialog from './account-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AccountDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AccountDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AccountDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={AccountDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AccountDetailsDeleteDialog} />
  </>
);

export default Routes;
