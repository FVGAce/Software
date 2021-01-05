classdef projectframework < matlab.apps.AppBase

    % Properties that correspond to app components
    properties (Access = public)
        UIFigure                     matlab.ui.Figure
        EnterModelYear19842020Label  matlab.ui.control.Label
        CarYear                      matlab.ui.control.NumericEditField
        ManufacturerDropDownLabel    matlab.ui.control.Label
        CarMake                      matlab.ui.control.DropDown
        ModelDropDownLabel           matlab.ui.control.Label
        CarModel                     matlab.ui.control.DropDown
        OptionEditionDropDownLabel   matlab.ui.control.Label
        CarOptions                   matlab.ui.control.DropDown
        Calculate                    matlab.ui.control.Button
        EnterMileageEditFieldLabel   matlab.ui.control.Label
        CarMileage                   matlab.ui.control.NumericEditField
        ConfirmYear                  matlab.ui.control.Button
        ConfirmMake                  matlab.ui.control.Button
        ConfirmModel                 matlab.ui.control.Button
        TotalCO2EmissionsgEditFieldLabel  matlab.ui.control.Label
        TotalCO2                     matlab.ui.control.NumericEditField
        UserPlots                    matlab.ui.control.UIAxes
        TextArea                     matlab.ui.control.TextArea
    end

    methods (Access = private)

        % Button pushed function: ConfirmYear
        function ConfirmYearButtonPushed(app, event)
        % First callback function saves the year chosen by the user
        % Makes a call to the API and stores every manufacturer from that year
        % in a cell array which becomes the options of the next drop down
            year = app.CarYear.Value;
            
            makes = webread('http://www.fueleconomy.gov/ws/rest/vehicle/menu/make?','year',year);
            makeslen = length(makes.menuItem);
            
            for i = 1:makeslen
                makescell{i} = makes.menuItem(i).text;
            end
            
            app.CarMake.Items = makescell;    
        end

        % Button pushed function: ConfirmMake
        function ConfirmMakeButtonPushed(app, event)
        % Confirm button confirms manufacturer, analyzes API for year and specified manufacturer
        % reads car model data into cell array which then becomes 'model' drop down 
            year = app.CarYear.Value;
            make = app.CarMake.Value;
            
            models = webread('http://www.fueleconomy.gov/ws/rest/vehicle/menu/model?','year',year,'make',make);
            modelslen = length(models.menuItem);
            
            for i = 1:modelslen
                modelcell{i} = models.menuItem(i).text;
            end
            
            app.CarModel.Items = modelcell;            
        end

        % Button pushed function: ConfirmModel
        function ConfirmModelButtonPushed(app, event)
        % Last confirm button confirms model and initializes the 'option/edition' drop down
        % the API data containing the option names is saved in the drop down menu
        % while the codes for the vehicles are saved as the value of the drop down menu
            year = app.CarYear.Value;
            make = app.CarMake.Value;
            model = app.CarModel.Value;
            
            options = webread('http://www.fueleconomy.gov/ws/rest/vehicle/menu/options?','year',year,'make',make,'model',model);
            optionslen = length(options.menuItem);
            
            for i = 1:optionslen
                optionscell{i} = options.menuItem(i).text;
                carID{i} = options.menuItem(i).value;
            end
            
            app.CarOptions.Items = optionscell;
            app.CarOptions.ItemsData = carID;
            
        end

        % Button pushed function: Calculate
        function CalculateEmissions(app, event)
        % Using the car code (carID) found from the option/edition drop down
        % we concatenate the API endpoint with the car code to call the API with
        % specific data for the exact vehicle. First we calculate total CO2 emissions
        % based on a user inputted mileage amount
           link = strcat('http://www.fueleconomy.gov/ws/rest/vehicle/',app.CarOptions.Value);
           data = webread(link);
           app.TotalCO2.Value = app.CarMileage.Value * str2num(data.co2TailpipeGpm); 
       
         % We open a new file called cardata.dat that will save each entry from each user
         % and save the car code and the specific co2 gram/mile amount using fprintf
           fid = fopen('cardata.dat','a');
           fprintf(fid,'%s %s\n',app.CarOptions.Value,data.co2TailpipeGpm);
           fclose(fid);
        
         % We load the cardata.dat file as a matrix and make bar graphs for each user
         % inputted vehicle (car code on x axis) and its CO2 grams/miles data on the y axis
         % We also plot a red line which indicates the average of the user inputted values
           load('cardata.dat');
           [r c] = size(cardata);
           x = 1:r;
           y = cardata(:,2);
           bar(app.UserPlots,x,y);
           
           average = sum(y)/r;
           yline(app.UserPlots, average,'r');
           for i = 1:r
           app.UserPlots.XTickLabel{i} = cardata(i,1);
           end
           
        end
    end

    % App initialization and construction
    methods (Access = private)

        % Create UIFigure and components
        function createComponents(app)

            % Create UIFigure
            app.UIFigure = uifigure;
            app.UIFigure.Position = [100 100 810 525];
            app.UIFigure.Name = 'UI Figure';

            % Create EnterModelYear19842020Label
            app.EnterModelYear19842020Label = uilabel(app.UIFigure);
            app.EnterModelYear19842020Label.HorizontalAlignment = 'right';
            app.EnterModelYear19842020Label.Position = [32 454 162 22];
            app.EnterModelYear19842020Label.Text = 'Enter Model Year(1984-2020)';

            % Create CarYear
            app.CarYear = uieditfield(app.UIFigure, 'numeric');
            app.CarYear.Limits = [1984 2020];
            app.CarYear.HorizontalAlignment = 'center';
            app.CarYear.Position = [63 433 100 22];
            app.CarYear.Value = 1984;

            % Create ManufacturerDropDownLabel
            app.ManufacturerDropDownLabel = uilabel(app.UIFigure);
            app.ManufacturerDropDownLabel.HorizontalAlignment = 'right';
            app.ManufacturerDropDownLabel.Position = [74 378 77 22];
            app.ManufacturerDropDownLabel.Text = 'Manufacturer';

            % Create CarMake
            app.CarMake = uidropdown(app.UIFigure);
            app.CarMake.Items = {};
            app.CarMake.Position = [47 357 136 22];
            app.CarMake.Value = {};

            % Create ModelDropDownLabel
            app.ModelDropDownLabel = uilabel(app.UIFigure);
            app.ModelDropDownLabel.HorizontalAlignment = 'right';
            app.ModelDropDownLabel.Position = [91 300 39 22];
            app.ModelDropDownLabel.Text = 'Model';

            % Create CarModel
            app.CarModel = uidropdown(app.UIFigure);
            app.CarModel.Items = {};
            app.CarModel.Position = [45 279 136 22];
            app.CarModel.Value = {};

            % Create OptionEditionDropDownLabel
            app.OptionEditionDropDownLabel = uilabel(app.UIFigure);
            app.OptionEditionDropDownLabel.HorizontalAlignment = 'right';
            app.OptionEditionDropDownLabel.Position = [71 222 83 22];
            app.OptionEditionDropDownLabel.Text = 'Option/Edition';

            % Create CarOptions
            app.CarOptions = uidropdown(app.UIFigure);
            app.CarOptions.Items = {};
            app.CarOptions.Position = [47 201 136 22];
            app.CarOptions.Value = {};

            % Create Calculate
            app.Calculate = uibutton(app.UIFigure, 'push');
            app.Calculate.ButtonPushedFcn = createCallbackFcn(app, @CalculateEmissions, true);
            app.Calculate.Position = [209 129 100 22];
            app.Calculate.Text = 'Calculate';

            % Create EnterMileageEditFieldLabel
            app.EnterMileageEditFieldLabel = uilabel(app.UIFigure);
            app.EnterMileageEditFieldLabel.HorizontalAlignment = 'right';
            app.EnterMileageEditFieldLabel.Position = [71 150 79 22];
            app.EnterMileageEditFieldLabel.Text = 'Enter Mileage';

            % Create CarMileage
            app.CarMileage = uieditfield(app.UIFigure, 'numeric');
            app.CarMileage.Position = [61 129 100 22];

            % Create ConfirmYear
            app.ConfirmYear = uibutton(app.UIFigure, 'push');
            app.ConfirmYear.ButtonPushedFcn = createCallbackFcn(app, @ConfirmYearButtonPushed, true);
            app.ConfirmYear.Position = [209 433 100 22];
            app.ConfirmYear.Text = 'Confirm';

            % Create ConfirmMake
            app.ConfirmMake = uibutton(app.UIFigure, 'push');
            app.ConfirmMake.ButtonPushedFcn = createCallbackFcn(app, @ConfirmMakeButtonPushed, true);
            app.ConfirmMake.Position = [209 357 100 22];
            app.ConfirmMake.Text = 'Confirm';

            % Create ConfirmModel
            app.ConfirmModel = uibutton(app.UIFigure, 'push');
            app.ConfirmModel.ButtonPushedFcn = createCallbackFcn(app, @ConfirmModelButtonPushed, true);
            app.ConfirmModel.Position = [209 279 100 22];
            app.ConfirmModel.Text = 'Confirm';

            % Create TotalCO2EmissionsgEditFieldLabel
            app.TotalCO2EmissionsgEditFieldLabel = uilabel(app.UIFigure);
            app.TotalCO2EmissionsgEditFieldLabel.HorizontalAlignment = 'right';
            app.TotalCO2EmissionsgEditFieldLabel.Position = [44 95 133 22];
            app.TotalCO2EmissionsgEditFieldLabel.Text = 'Total CO2 Emissions (g)';

            % Create TotalCO2
            app.TotalCO2 = uieditfield(app.UIFigure, 'numeric');
            app.TotalCO2.Limits = [0 Inf];
            app.TotalCO2.ValueDisplayFormat = '%.2f';
            app.TotalCO2.Position = [61 66 100 22];

            % Create UserPlots
            app.UserPlots = uiaxes(app.UIFigure);
            title(app.UserPlots, 'User Entered Car Emissions in CO2 Grams per Mile')
            xlabel(app.UserPlots, 'API CarID')
            ylabel(app.UserPlots, 'CO2 Emissions (grams/mile)')
            app.UserPlots.XTickLabelRotation = 270;
            app.UserPlots.Position = [356 152 435 277];

            % Create TextArea
            app.TextArea = uitextarea(app.UIFigure);
            app.TextArea.Position = [392 15 364 134];
            app.TextArea.Value = {'According to the EPA, the average passenger vehicle emits ~404 grams of CO2 per mile.'; ''; 'The red line on the graph represents the average of user inputted vehicles.'; ''; 'API Endpoint Used : http://www.fueleconomy.gov/ws/rest/vehicle/'; 'The CarID on the plot can be entered to the end of the link to find additional data'; ''};
        end
    end

    methods (Access = public)

        % Construct app
        function app = projectframework

            % Create and configure components
            createComponents(app)

            % Register the app with App Designer
            registerApp(app, app.UIFigure)

            if nargout == 0
                clear app
            end
        end

        % Code that executes before app deletion
        function delete(app)

            % Delete UIFigure when app is deleted
            delete(app.UIFigure)
        end
    end
end